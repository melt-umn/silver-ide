grammar driver;

nonterminal BuildEnv with silverIDEHome, specPath, defaultSpecPath;
{-- Find jars, standard library -}
synthesized attribute silverIDEHome :: String;
{-- Path for source specification files to use -}
synthesized attribute specPath :: [String];

synthesized attribute defaultSpecPath :: String; -- Just the stdlib, so not actually just the default value

{--
 - Build environment information.
 - Note: each of these paths always terminates with /
 - TODO: consider moving svParser, args to this structure too? (e.g. clean flag, build grammar)
 - maybe also buildGrammar?
 -}
abstract production buildEnv
top::BuildEnv ::= silverIDEHome::String specPath::[String]
{
  top.silverIDEHome = silverIDEHome;
  top.specPath = specPath;

  -- So that this exists in exactly one location:
  top.defaultSpecPath = silverIDEHome ++ "ide/";
}

-- Takes environment and values from args and determines buildEnv according to correct priorities.
function buildEnvFromArgsAndMachineEnv
BuildEnv ::=
  SILVER_IDE_HOME::String -- empty string or value
  SPEC_PATH::[String] -- any number of values
  --homeArg::[String] -- empty list or one value
  pathArg::[String] -- any number of values
{
  -- If provided with one, use that, otherwise always use the environment value (if empty, use that)
  local silverIDEHome :: String =
      endWithSlash(head([SILVER_IDE_HOME]));
--    endWithSlash(head(homeArg ++ [SILVER_HOME]));

  -- Use the arguments (all of them), followed by environment (if any?), followed by STDLIB, and CWD.
{--  local specPath :: [String] =
    map(endWithSlash,
      pathArg ++
      SPEC_PATH ++
      [benv.defaultSpecPath] ++
      ["."]);
--}
  -- use just these for now 
  local specPath :: [String] = map(endWithSlash, SPEC_PATH ++ pathArg);

  local benv :: BuildEnv = buildEnv(silverIDEHome, specPath);

  return benv;
}

function determineBuildEnv
IOVal<Either<[String] BuildEnv>> ::= args::Decorated CmdArgs  ioin::IO
{
  -- Let's locally set up and verify the environment
  local envHome :: IOVal<String> = envVar("SILVER_IDE_HOME", ioin);
  local envPath :: IOVal<String> = envVar("IDE_SPEC_PATH", envHome.io);

  -- If SILVER_HOME isn't set, TODO: determine it from where this jar is
  -- right now just error
  local derivedHome :: IOVal<String> =
    if envHome.iovalue == "" then
      ioval(envPath.io, "")
      --determineDefaultSilverHome(envSG.io)
    else
      ioval(envPath.io, envHome.iovalue);

  local benv :: BuildEnv =
    buildEnvFromArgsAndMachineEnv(
      derivedHome.iovalue, [envPath.iovalue], args.searchPath);

    -- Let's do some checks on the environment
  local envErrors :: IOVal<[String]> = errorCheckEnv(benv, derivedHome.io);
  
  return if null(envErrors.iovalue) then
    ioval(envErrors.io, right(benv))
  else
    ioval(envErrors.io, left(envErrors.iovalue));
}

function errorCheckEnv
IOVal<[String]> ::= benv::BuildEnv ioin::IO
{
  local isSpecDir :: IOVal<Boolean> = isDirectory(benv.defaultSpecPath, ioin);
  local errors :: [String] = []; -- dont need these things yet.
  {--
    if benv.silverIDEHome == "/" -- because we called 'endWithSlash' on empty string
    then ["Missing SILVER_IDE_HOME environment variable"] --or --silver-home <path>.\nThis should have been set up by the 'silver' script.\n"]
    else if !isSpecDir.iovalue
    then ["Missing standard library specifications: tried " ++ benv.defaultSpecPath ++ " but this did not exist.\n"]
    else []; 
--}
  return ioval(isSpecDir.io, errors);
}

{-- TODO: Implement this function
function determineDefaultSilverHome
IOVal<String> ::=  i::IO
{
}
--}
