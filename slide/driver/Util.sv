grammar slide:driver;

{--
 - Determined whether a file name should be considered a Silver source file.
 -}
function isValidSilverIDEFile
Boolean ::= fileExt::String file::String
{
  return endsWith(fileExt, file) && !startsWith(".", file);
}
function listSilverIDEFiles
IOVal<[String]> ::= dir::String  fileExt::String ioin::IO
{
  local files :: IOVal<[String]> = listContents(dir, ioin);

  return ioval(files.io, filter(isValidSilverIDEFile(fileExt, _), files.iovalue));
}



{-- UTIL CODE IN SILVER 
  COULD DELETE IF MOVED TO SILVER CORE OTHERWISE HAVING IT HERE IS EASIER
  THAN IMPORTING 
  could also import but that leads to a bunch of other junk --}
{--
 - Ensures a string ends with a forward slash. Safe to use if it already has one.
 -}
function endWithSlash
String ::= s::String
{
  return if endsWith("/", s) then s else s ++ "/";
}

-- A crude approximation of line wrapping
function renderFileNames
String ::= files::[String]  depth::Integer
{
  return
    if null(files) then "" else
    if depth >= 7 then "\n\t " ++ renderFileNames(files, 0) else
    head(files) ++
    if null(tail(files)) then "" else " " ++ renderFileNames(tail(files), depth + 1);
}
