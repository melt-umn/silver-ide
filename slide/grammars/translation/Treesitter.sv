grammar translation;

import abstractsyntax;
import silver:extension:treesitter;

function modifyTreesitterGrammarJs 
String ::= spec::Spec grammarJs::String
{
  local ignoreButHighlight :: [String] = spec.highlightableIgnoreTerminals;
  return foldl(removeIgnoreFromTreesitterTerminal, grammarJs, ignoreButHighlight);
}

-- Changes an ignore terminal in Treesitter something with a leading _
-- to not be removed. This is necessary for highlighting.
function removeIgnoreFromTreesitterTerminal
String ::= str::String termName::String 
{
  return substitute("_"++toTsDeclaration(termName), toTsDeclaration(termName), str);
}
