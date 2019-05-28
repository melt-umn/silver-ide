imports slide:concretesyntax;
imports slide:abstractsyntax;
imports slide:ideinterface;
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
{--
  local result::IOMonad<Integer> = do (bindIO, returnIO) {
    if null(args) then {
      printM("Usage: [ableC invocation] [file name] [c preprocessor arguments]\n");
      return 5;
    } else {
      isF::Boolean <- isFileM(fileName);
      if !isF then {
        printM("File \"" ++ fileName ++ "\" not found.\n");
        return 1;
      } else {
        if containsBy(stringEq, "--show-cpp", args) then
          printM("CPP command: " ++ fullCppCmd ++ "\n");
        mkCppFile::Integer <-
          if skipCpp then returnIO(0)
          else systemM(fullCppCmd);
        if mkCppFile != 0 then {
          printM("CPP call failed: " ++ fullCppCmd ++ "\n");
          return 3;
        } else {
          text :: String <- readFileM(cppFileName);
          result :: ParseResult<cst:Root> = theParser(text, cppFileName);
          if !result.parseSuccess then {
            printM(result.parseErrors ++ "\n");
            return 2;
          } else {
            comp :: Decorated abs:Compilation =
              decorate abs:compilation(result.parseTree.ast) with {
                env = addEnv( map(xcArgDef, xcArgs) , emptyEnv() );
              };
            if containsBy(stringEq, "--show-ast", args) then {
              printM(substitute("edu:umn:cs:melt:", "", hackUnparse(comp.abs:srcAst)) ++ "\n");
              return 0;
            }
            else if containsBy(stringEq, "--show-host-ast", args) then {
              printM(substitute("edu:umn:cs:melt:", "", hackUnparse(comp.abs:hostAst)) ++ "\n");
              return 0;
            }
            else if containsBy(stringEq, "--show-lifted-ast", args) then {
              printM(substitute("edu:umn:cs:melt:", "", hackUnparse(comp.abs:liftedAst)) ++ "\n");
              return 0;
            }
            else if containsBy(stringEq, "--show-pp", args) then {
              printM(show(100, comp.abs:srcPP) ++ "\n");
              return 0;
            }
            else if containsBy(stringEq, "--show-host-pp", args) then {
              printM(show(100, comp.abs:hostPP) ++ "\n");
              return 0;
            }
            else if containsBy(stringEq, "--show-lifted-pp", args) then {
              printM(show(100, comp.abs:liftedPP) ++ "\n");
              return 0;
            }
            else {
              if !null(comp.errors) then
                printM(messagesToString(comp.errors) ++ "\n");
              if containsBy(stringEq, "--force-trans", args) || !containsErrors(comp.errors, false) then
                writeFileM(ppFileName, show(80, comp.abs:finalPP));
              if containsErrors(comp.errors, false) then
                return 4;
              else
                return 0;
            }
          }
        }
      }
    }
  };

  return evalIO(result, ioIn);
--}
