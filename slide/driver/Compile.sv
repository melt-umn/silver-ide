grammar slide:driver;

{--
 - Eat the stream `need` and produce the output stream of (maybe, if found) Specs
 -
 - @param benv   The compiler configuration, including search paths
 - @param need   A **stream** of direcotires containing specifications to compile.
 -}
function compileSpecs
IOVal<Spec> ::=
  specParser::SpecParser
  args::Decorated CmdArgs
  need::[String]
  ioin::IO
{
  -- A stream of directories containing specifications to compile
--  local need::[String] = benv.specPath;

  local specDir :: String = head(need);
  
  -- Build the first gramamr in the need list.
  local now :: IOVal<Spec> =
    compileSpecDirectory(specParser, args, specDir, ioin);

  -- Recurse for the rest of the grammars needed.
  local recurse :: IOVal<Spec> =
    compileSpecs(specParser, args, tail(need), now.io);

  return
    if null(need) then
      ioval(ioin, nilSpec())
    else
      -- unsafeTrace is needed to force the IO
      ioval(recurse.io, appendSpecs(unsafeTrace(now.iovalue, now.io), recurse.iovalue));
}


{--
 - Builds the Specification for the spec files in the directory  and obtains its symbols, either by building or from an interface file.
 -}
function compileSpecDirectory
IOVal<Spec> ::=
  specParser::SpecParser
  args::Decorated CmdArgs
  specDirectory::String
  ioin::IO
{
  local ideFileExt :: String = 
    if null(args.specFileExt) then "svide" else head(args.specFileExt);
  -- IO Step 1: Get the spec files in the directory 
  local files :: IOVal<[String]> = listSilverIDEFiles(specDirectory, ideFileExt, ioin);

  -- IO Step 2: Build the spec directory
  local pr :: IO =
    print("Using directory " ++ specDirectory ++ " which contains these specifications\n\t[" ++ renderFileNames(files.iovalue, 0) ++ "]\n", files.io);

  local specCompile :: IOVal<Pair<[SpecRoot] [ParseError]>> =
    compileFiles(specParser, specDirectory, files.iovalue, pr);

  return 
    -- no parse errors
    if null(specCompile.iovalue.snd) then
     ioval(specCompile.io, foldr(consSpec, nilSpec(), map((.specification), specCompile.iovalue.fst)))
    else
     ioval(specCompile.io, errorSpec(map((.parseErrors), specCompile.iovalue.snd), [specDirectory]));

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
IOVal<Pair<[SpecRoot] [ParseError]>> ::= specParser::SpecParser  dir::String  files::[String]  ioin::IO
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
          error(errval.parseErrors)
      --    ioval(recurse.io, pair(recurse.iovalue.fst, errval :: recurse.iovalue.snd))
      end;
}

