grammar driver;

{--
 - Eat the stream `need` and produce the output stream of (maybe, if found) Specs
 - @param specParser The parser for specification files
 - @param specList The list of specifications to be kept
 - @param args   The command line arguments
 - @param need   A **stream** of direcotires containing specifications to compile.
 -}
function compileSpecs
IOVal<Either<[ParseError] Spec>> ::=
  specParser::SpecParser
  specList::[String]
  args::Decorated CmdArgs
  need::[String]
  ioin::IO
{
  -- A stream of directories containing specifications to compile
--  local need::[String] = benv.specPath;

  local specDir :: String = head(need);
  
  -- Build the first gramamr in the need list.
  local now :: IOVal<Either<[ParseError] Spec>> =
    compileSpecDirectory(specParser, specList, args, specDir, ioin);

  -- Recurse for the rest of the grammars needed.
  local recurse :: IOVal<Either<[ParseError] Spec>> =
    compileSpecs(specParser, specList, args, tail(need), now.io);

   -- unsafeTrace is needed to force the IO
  local nowValue :: Either<[ParseError] Spec> = unsafeTrace(now.iovalue, now.io);

  local combined :: IOVal<Either<[ParseError] Spec>> =
    if nowValue.isLeft && recurse.iovalue.isLeft 
    -- combine errors
    then ioval(recurse.io, left(nowValue.fromLeft ++ recurse.iovalue.fromLeft))
    else if nowValue.isLeft
    then ioval(recurse.io, nowValue)
    else if recurse.iovalue.isLeft
    then ioval(recurse.io, recurse.iovalue)
    else ioval(recurse.io, right(appendSpecs(nowValue.fromRight, recurse.iovalue.fromRight)));

  return
    if null(need) then
      ioval(ioin, right(nilSpec()))
    else
      combined;
}


{--
 - Builds the Specification for the spec files in the directory  and obtains its symbols, either by building or from an interface file.
 -}
function compileSpecDirectory
IOVal<Either<[ParseError] Spec>> ::=
  specParser::SpecParser
  specList::[String]
  args::Decorated CmdArgs
  specDirectory::String
  ioin::IO
{
  -- IO Step 1: Get the spec files in the directory 
  local files :: IOVal<[String]> = listSlideFiles(specDirectory, ioin);

  -- IO Step 2: Build the spec directory
  local pr :: IO =
    print("Using directory " ++ specDirectory ++ " which contains these specifications\n\t[" ++ renderFileNames(files.iovalue, 0) ++ "]\n", files.io);

  local specCompile :: IOVal<Pair<[SpecRoot] [ParseError]>> =
    compileFiles(specParser, specDirectory, files.iovalue, pr);

  -- filter out specs that are not specified in the spec list file
  local filteredSpecs :: [SpecRoot] = filter(inSpecList(specList, _), specCompile.iovalue.fst);
  return 
    -- no parse errors
    if null(specCompile.iovalue.snd) then
     ioval(specCompile.io, right(foldr(consSpec, nilSpec(), map((.specification), filteredSpecs))))
    else
     ioval(specCompile.io, left(specCompile.iovalue.snd));

}

{--
 - Parses a list of files.
 - @param svParser  The parser to use to contruct Roots
 - @param gpath  The path where we found the grammar. Ends in a slash/
 - @param files  The list of .sv files to read.
 - @param ioin  The io token
 - @return An ioval wrapping the list of parse results and parse errors.
 -}
function compileFiles
IOVal<Pair<[SpecRoot] [ParseError]>> ::= 
  specParser::SpecParser  
  dir::String  
  files::[String]  
  ioin::IO
{
  local file :: String = head(files);
  
  -- Print the path we're reading, and read the file.
  local fileText :: IOVal<String> =
    readFile(dir ++ file, ioin);

   -- This is where a spec file actually gets parsed:
  local result :: ParseResult<SpecRoot> = specParser(fileText.iovalue, file);

  -- Continue parsing the rest of the files.
  local recurse :: IOVal<Pair<[SpecRoot] [ParseError]>> = compileFiles(specParser, dir, tail(files), fileText.io);

  return 
    if null(files) then ioval(ioin, pair([], []))
    -- Using [] in this case because there seems to be no end to io token demanding issues:
    else 
      case result of
      | parseSucceeded(rtree, _) ->
          ioval(recurse.io, pair(rtree :: recurse.iovalue.fst, recurse.iovalue.snd))
      | parseFailed(errval, _) ->
      --    error(errval.parseErrors)
          ioval(recurse.io, pair(recurse.iovalue.fst, errval :: recurse.iovalue.snd))
      end;
}


function inSpecList
Boolean ::= specList::[String] spec::SpecRoot
{
  return containsBy(stringEq, spec.specificationName, specList);
}
