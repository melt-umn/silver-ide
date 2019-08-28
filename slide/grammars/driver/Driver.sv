imports concretesyntax;
imports concretesyntax:specList;
imports abstractsyntax;
imports ideinterface;
imports parsers;
imports translation;
imports silver:extension:ideinterface;
imports silver:extension:treesitter;

imports core:monad; -- for io
import silver:reflect; -- necessary otherwise the deserializeAST function is not known

type SpecParser = (ParseResult<SpecRoot> ::= String String);
-- A common return type for IO functions. Does IO and returns error or whatever.
--type IOErrorable<a> = IOVal<Either<RunError a>>;

function driver
IOVal<Integer> ::= args::[String] specParser::SpecParser ioIn::IO
{
  local cmdArgParseResult :: Either<String Decorated CmdArgs> = parseArgs(args);
  local cmdArgs :: Decorated CmdArgs = cmdArgParseResult.fromRight;

  local buildEnvResult :: IOVal<Either<[String] BuildEnv>> = determineBuildEnv(cmdArgs, ioIn);
  local buildEnv :: BuildEnv = buildEnvResult.iovalue.fromRight;

  local specListFile :: String = head(tail(cmdArgs.cmdRemaining));
  local specListExists :: IOVal<Boolean> = isFile(specListFile, buildEnvResult.io);

  local specListFileText :: IOVal<String> = readFile(specListFile, specListExists.io);
  local specListParse :: ParseResult<SpecNameList> = specNameListParser(specListFileText.iovalue, specListFile);
  local specList :: [String] = specListParse.parseTree.specNames;

  local specCompile :: IOVal<Either<[ParseError] Spec>> = 
    compileSpecs(specParser, specList, cmdArgs, buildEnv.specPath, specListFileText.io);
  local spec :: Spec = specCompile.iovalue.fromRight;

  local metadataFile :: String = head(cmdArgs.cmdRemaining);
  local metadataFileText :: IOVal<String> = readFile(metadataFile, specCompile.io);

  local ideInterfaceEither :: Either<String IDEInterfaceSyntaxRoot> = 
    deserialize(metadataFile, metadataFileText.iovalue);
  local ideInterface :: IDEInterfaceSyntaxRoot = ideInterfaceEither.fromRight;
 
  local decIDEInterface :: Decorated IDEInterfaceSyntaxRoot = 
    decorate ideInterface with { spec = spec; buildEnv = buildEnv; };

  local languageName :: String = spec.langName.fromJust;


  -- start things to do if treesitter flag is provided
  local modifyingTreesitter :: Boolean = !null(cmdArgs.treesitterFile);
  local treesitterFile :: String = head(cmdArgs.treesitterFile);
  local treesitterFileExists :: IOVal<Boolean> = isFile(treesitterFile, metadataFileText.io);
  local treesitterText :: IOVal<String> = readFile(treesitterFile, treesitterFileExists.io);
  local newGrammarJs :: String = modifyTreesitterGrammarJs(spec, treesitterText.iovalue);

  --}
  local cmdArgErrors :: Pair<String Integer> = getCmdArgErrors(cmdArgParseResult, cmdArgs);
  local specListErrors :: Pair<String Integer> = getSpecListErrors(specListExists, specListFile, specListParse);
  local specErrors :: Pair<String Integer> = getSpecErrors(specCompile, spec);

  local result::IOMonad<Integer> = do (bindIO, returnIO) {
    if snd(cmdArgErrors) != 0 then { 
      printM(fst(cmdArgErrors));
      return snd(cmdArgErrors);
    }
    else if snd(specListErrors) != 0 then {
      printM(fst(specListErrors));
      return snd(specListErrors);
    }
    else if buildEnvResult.iovalue.isLeft then { -- error building environment
      printM(implode("\n", buildEnvResult.iovalue.fromLeft));
      return 5;
    }
    else if snd(specErrors) != 0 then {
      printM(fst(specErrors));
      return snd(specErrors);
    }
    else if ideInterfaceEither.isLeft then {
      printM(ideInterfaceEither.fromLeft);
      return 10;
    }
    else if cmdArgs.wantAtomLSPFile && !spec.lspJarName.isJust then {
      printM("LSP jar name not specified as a global property of your language.\n");
      return 11;
    }
    else if cmdArgs.wantAtomLanguageFile && !spec.treesitterParserName.isJust then {
      printM("No language parser property was specified.\n");
      return 3;
    }
    else {
      if cmdArgs.wantAtomLanguageFile then {
        printM("Received --atom-language-file flag printing out " ++ languageName ++ ".cson\n");
        writeFileM(languageName ++ ".cson", decIDEInterface.atomLanguageFile);
      }
      if cmdArgs.wantAtomLSPFile then {
        printM("Received --atom-lsp-file flag printing out " ++ "AtomPackageLSPMain.js\n");
        writeFileM("AtomPackageLSPMain.js", decIDEInterface.atomLSPFile);
      }
      if modifyingTreesitter then {
        printM("Writing new grammar.js file after specification\n");
        printM("Making these terminals highlightable:\n" ++ implode("\n", spec.highlightableIgnoreTerminals) ++ "\n");
        writeFileM("grammar.js", newGrammarJs);
      }
      return 0;
    }
  };

  return evalIO(result, ioIn);
}

function getCmdArgErrors
Pair<String Integer> ::= parseResult::Either<String Decorated CmdArgs> cmdArgs::Decorated CmdArgs
{
  return
  if parseResult.isLeft
  then pair(parseResult.fromLeft ++ "\n", 1)
  else if null(cmdArgs.cmdRemaining)
  then pair("No ide interface metadata file provided. Run silver with --ide-interface flag with the grammar to generate this file\n", 2)
  else if null(tail(cmdArgs.cmdRemaining))
  then pair("No specification list provided\n", 3)
  else pair("", 0);
}

function getSpecListErrors
Pair<String Integer> ::= specListExists::IOVal<Boolean> specListFile::String parseAttempt::ParseResult<SpecNameList>
{
  return
  if !specListExists.iovalue
  then pair("Specification list file could not be found\n", 1)
  else if !parseAttempt.parseSuccess 
  then pair("Failed parsing specification file list " ++ specListFile ++ " with error " ++ parseAttempt.parseError.parseErrors ++ "\n", 2)
  else pair("", 0);
}

function getSpecErrors
Pair<String Integer> ::= parseAttempt::IOVal<Either<[ParseError] Spec>> spec::Spec
{
  return
  if parseAttempt.iovalue.isLeft
  then pair("Errors parsing specification files:\n" ++ implode("\n", map((.parseErrors), parseAttempt.iovalue.fromLeft)), 1)
  else if !spec.langName.isJust
  then pair("No global language name property was specified.\n", 2)
  else pair("", 0);
}
