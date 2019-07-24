imports concretesyntax;
imports concretesyntax:specList;
imports abstractsyntax;
imports ideinterface;
imports parsers;
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

  local specList_file :: String = head(tail(cmdArgs.cmdRemaining));
  local specList_file_text :: IOVal<String> = readFile(specList_file, buildEnvResult.io);
  local specListParse :: ParseResult<SpecNameList> = specNameListParser(specList_file_text.iovalue, specList_file);
  local specList :: [String] = specListParse.parseTree.specNames;

  local specCompile :: IOVal<Either<[ParseError] Spec>> = 
    compileSpecs(specParser, specList, cmdArgs, buildEnv.specPath, specList_file_text.io);
  local spec :: Spec = specCompile.iovalue.fromRight;

  local metadata_file :: String = head(cmdArgs.cmdRemaining);
  local metadata_file_text :: IOVal<String> = readFile(metadata_file, specCompile.io);

  local ideInterfaceEither :: Either<String IDEInterfaceSyntaxRoot> = 
    deserialize(metadata_file, metadata_file_text.iovalue);
  local ideInterface :: IDEInterfaceSyntaxRoot = ideInterfaceEither.fromRight;
 
  local decIDEInterface :: Decorated IDEInterfaceSyntaxRoot = 
    decorate ideInterface with { spec = spec; };

  local languageName :: String = spec.langName.fromJust;

  -- start things to do if treesitter flag is provided
  {--
  local treesitter_serialized_spec :: String = head(cmdArgs.treesitterFile);
  local treesitter_text :: IOVal<String> = readFile(treesitter_serialized_text, metadata_file_text.io)

  local treesitterEither = Either<String TreesitterRoot> =
    deserialize(treesitter_serialized_spec, treesitter_text);
  local treesitterRoot :: TreesitterRoot = treesitterEither.fromRight;

  local decTreesitterRoot :: TreesitterRoot =
    modifyTreesitterGrammar(treesitterRoot
  -- end things to do if treesitter flag is provided
  --}

  local result::IOMonad<Integer> = do (bindIO, returnIO) {
    if cmdArgParseResult.isLeft then { -- error parsing command line
      printM(cmdArgParseResult.fromLeft ++ "\n");
      return 1;
    } 
    else if null(cmdArgs.cmdRemaining) then {
      printM("No ide interface metadata file provided. Run silver with --ide-interface flag with the grammar to build to generate this file\n");
      return 2;
    } 
    else if null(tail(cmdArgs.cmdRemaining)) then {
      printM("No specification list file provided.\n");
      return 3;
    } 
    else if buildEnvResult.iovalue.isLeft then { -- error building environment
      printM(implode("\n", buildEnvResult.iovalue.fromLeft));
      return 4;
    } 
    else if !specListParse.parseSuccess then {
      printM("Failed parsing specification file list " ++ specList_file ++ "with error " ++ specListParse.parseError.parseErrors ++ "\n");
      return 5;
    } 
    else if (specCompile.iovalue.isLeft) then {
      printM("Errors parsing specification files:\n" ++ implode("\n", map((.parseErrors), specCompile.iovalue.fromLeft)));
      return 6;
    }
    else if !spec.langName.isJust then {
      printM("No global language name property was specified.\n");
      return 7;
    } 
    else if !spec.treesitterParserName.isJust then {
      printM("No language parser property was specified.\n");
      return 8;
    } 
    else if ideInterfaceEither.isLeft then {
      printM(ideInterfaceEither.fromLeft);
      return 9;
    } 
    else {
      writeFileM(languageName ++ ".cson", decIDEInterface.atomGrammarFile);
      return 0;
    }
  };

  return evalIO(result, ioIn);
}
