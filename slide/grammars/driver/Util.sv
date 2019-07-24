grammar driver;

{--
 - Determined whether a file name should be considered a Silver source file.
 -}
function isValidSlideFile
Boolean ::= file::String
{
  return endsWith(".slide", file) && !startsWith(".", file);
}
function listSlideFiles
IOVal<[String]> ::= dir::String  ioin::IO
{
  local files :: IOVal<[String]> = listContents(dir, ioin);

  return ioval(files.io, filter(isValidSlideFile, files.iovalue));
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
