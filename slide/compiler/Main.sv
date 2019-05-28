grammar slide:compiler;

import slide:parsers;
import slide:driver;

function main
IOVal<Integer> ::= args::[String] ioin::IO
{
  return driver(args, specFileParser, ioin);
}
