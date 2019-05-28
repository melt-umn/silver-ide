grammar compiler;

import parsers;
import driver;

function main
IOVal<Integer> ::= args::[String] ioin::IO
{
  return driver(args, specFileParser, ioin);
}
