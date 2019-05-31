grammar driver;

nonterminal CmdArgs with cmdRemaining, cmdError;

synthesized attribute cmdRemaining :: [String];
synthesized attribute cmdError :: Maybe<String>;

nonterminal Flag with flagInput, flagOutput, flagBeforeAst, flagAfterAst;

-- the input of the command line before the flag is processed
inherited attribute flagInput::[String];
-- the rest of the command line that still needs to be processed after this flag
synthesized attribute flagOutput::[String];
-- this represents the command args before the abstract productions are run 
inherited attribute flagBeforeAst::CmdArgs;
-- this represents the command args after the abstract productions are run
synthesized attribute flagAfterAst::CmdArgs;


synthesized attribute searchPath :: [String];
synthesized attribute specFileExt :: [String];
synthesized attribute treesitterFile :: [String];
attribute searchPath, specFileExt, treesitterFile occurs on CmdArgs;

{--
 - For defining base, default values for any attributes on CmdArgs
 -}
abstract production endCmdArgs
top::CmdArgs ::= remaining::[String]
{
  top.cmdRemaining = remaining;
  top.cmdError = nothing();

  top.searchPath = [];
  top.specFileExt = [];
  top.treesitterFile = [];
}

{--
 - Only used when an error is encountered attempting to parse an option.
 - One should always check for .cmdError.isJust BEFORE accessing any other attributes.
 -}
abstract production errorCmdArgs
top::CmdArgs ::= errmsg::String
{
  top.cmdRemaining = [];
  top.cmdError = just(errmsg);
  forwards to endCmdArgs([]); -- Well, this is an abuse, but this whole thing is an abuse, really.
}

abstract production includeFlag
top::CmdArgs ::= s::String rest::CmdArgs
{
  top.searchPath = s :: forward.searchPath;
  forwards to rest;
}

abstract production extFlag
top::CmdArgs ::= s::String rest::CmdArgs
{
  top.specFileExt = s :: forward.specFileExt;
  forwards to rest;
}

abstract production treesitterFlag
top::CmdArgs ::= s::String rest::CmdArgs
{
  top.treesitterFile = s::forward.treesitterFile;
  forwards to rest;
}

{--
 - As in the terminology used in Silver, a 'flag' is a cmd line option
 - with no parameters.
 -}
abstract production flag
top::Flag ::= ast::(CmdArgs ::= CmdArgs)
{
  -- process the one argument input
  top.flagOutput = tail(top.flagInput);
  top.flagAfterAst = ast(top.flagBeforeAst);
}

{--
 - As in the terminology used in Silver, an 'option' is a cmd line option
 - with one, single parameter.
 -}
abstract production option
top::Flag ::= ast::(CmdArgs ::= String CmdArgs)
{
  -- check if there is a parameter provided if there is consume both inputs
  top.flagOutput = if null(tail(top.flagInput)) then [] else tail(tail(top.flagInput));
  -- error if no parameter is provided otherwise transform the CmdArg
  top.flagAfterAst = if null(tail(top.flagInput))
                     then errorCmdArgs(head(top.flagInput) ++ " is missing its parameter.")
                     else ast(head(tail(top.flagInput)), top.flagBeforeAst);
}

function parseArgs
Either<String  Decorated CmdArgs> ::= args::[String]
{
  production attribute flags::[Pair<String Flag>] with ++;
  flags := [];
  production attribute flagdescs::[String] with ++;
  flagdescs := [];

  -- General rules of thumb (from Silver):
  --  Use -- as your prefix
  --  Unless it's an OPTION, and it's commonly used, and it's obvious from context what it means
  -- e.g. -I my/specs is obvious because it refers to a location to include.
  flags <- [
    pair("-I", option(includeFlag)),
    pair("--ext", option(extFlag)),
    pair("--treesitter", option(treesitterFlag))
  ];
  flagdescs <- [
   -- Always start with \t, name options descriptively in <>, do not end with \n!
    "\t-I <path>   : path to specification files (SPEC_PATH)",
    "\t--ext <ext> : use the specified file extension when looking for spec files",
    "\t--treesitter <serialized treesitter file> : modify the Treesitter grammar according to the specification"
  ];

  local usage :: String =
    "Usage: slide [options] ide:interface:metadata:file\n\nFlag options:\n" ++ implode("\n", sortBy(stringLte, flagdescs)) ++ "\n";
  
  -- Parse the command line
  production a :: CmdArgs = interpretCmdArgs(flags, args);

  production attribute errors :: [String] with ++;
  errors := if a.cmdError.isJust then [a.cmdError.fromJust] else [];

  errors <-
    if length(a.cmdRemaining) > 1 then ["Unable to interpret arguments: " ++ implode(" ", a.cmdRemaining)]
    else if length(a.specFileExt) > 1 then ["Multiple options given for --ext flag: " ++ implode(" ", a.specFileExt)]
    else if length(a.treesitterFile) > 1 then ["Multiple options given for --treesitter flag: " ++ implode(" ", a.treesitterFile)]
    else [];

  return if !null(errors)
         then left(implode("\n", errors) ++ "\n\n" ++ usage)
         else right(a);
}

function interpretCmdArgs
CmdArgs ::= flags::[Pair<String Flag>]  input::[String]
{
  local attribute l :: Maybe<Flag>;
  l = lookupBy(stringEq, head(input), flags);

  local attribute here :: Flag;
  here = l.fromJust;
  here.flagInput = input;
  -- process the rest of arguments before transforming attributes
  here.flagBeforeAst = interpretCmdArgs(flags, here.flagOutput);

  return if null(input) then endCmdArgs([])
         else if !l.isJust
         then if startsWith("-", head(input))
              then errorCmdArgs("Unrecognized flag: " ++ head(input))
              else endCmdArgs(input)
         else here.flagAfterAst;
}
