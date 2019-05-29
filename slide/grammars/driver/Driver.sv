imports concretesyntax;
imports abstractsyntax;
imports ideinterface;
imports silver:extension:ideinterface;

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

  local spec :: IOVal<Spec> = compileSpecs(specParser, cmdArgs, buildEnv.specPath, buildEnvResult.io);

  local metadata_file :: String = head(cmdArgs.cmdRemaining);
  local metadata_file_text :: IOVal<String> = readFile(metadata_file, spec.io);

  local ideInterfaceEither :: Either<String IDEInterfaceSyntaxRoot> = 
    deserialize(metadata_file, metadata_file_text.iovalue);
  local ideInterface :: IDEInterfaceSyntaxRoot = ideInterfaceEither.fromRight;
 
  local decIDEInterface :: Decorated IDEInterfaceSyntaxRoot = 
    decorate ideInterface with { spec = spec.iovalue; };

  local languageName :: String = spec.iovalue.langName.fromJust;

  local result::IOMonad<Integer> = do (bindIO, returnIO) {
    if cmdArgParseResult.isLeft then { -- error parsing command line
      printM(cmdArgParseResult.fromLeft ++ "\n");
      return 1;
    } else if null(cmdArgs.cmdRemaining) then {
      printM("No ide interface metadata file provided. Run silver with --ide-interface flag with the grammar to build to generate this file\n");
      return 2;
    } else if buildEnvResult.iovalue.isLeft then { -- error building environment
      printM(implode("\n", buildEnvResult.iovalue.fromLeft));
      return 3;
    } else if !spec.iovalue.langName.isJust then {
      printM("No global language name property was specified.\n");
      return 4;
    } else if !spec.iovalue.treesitterParserName.isJust then {
      printM("No language parser property was specified.\n");
      return 5;
    } else if ideInterfaceEither.isLeft then {
      printM(ideInterfaceEither.fromLeft);
      return 5;
    } else {
      writeFileM(languageName ++ ".cson", decIDEInterface.atomGrammarFile);
      return 0;
    }
  };

  return evalIO(result, ioIn);
}
