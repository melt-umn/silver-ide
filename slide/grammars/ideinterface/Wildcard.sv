
function matchesWildcardCriteria
Boolean ::= dcl::IDEInterfaceSyntaxDcl wc::WildcardCriteria 
{
  local attribute grammarName :: String = substring(0, lastIndexOf(":", dcl.dclName), dcl.dclName);
  local attribute grammarMatch :: Boolean =
    if wc.requiredGrammar.isJust
    then stringEq(wc.requiredGrammar.fromJust, grammarName)
    else true;

  local attribute correctDclType :: Boolean =
    (wc.mustBeTerminal && isTerminal(dcl)) -- it must be a terminal and is
    || (wc.mustBeNonterminal && isNonterminal(dcl)) -- or it must be a terminal and is
    || (!wc.mustBeTerminal && !wc.mustBeNonterminal); -- or it does not need to be a terminal or nonterminal

    return correctDclType && grammarMatch;
}

function lookupAllMatchingCriteria
[a] ::= dcl::IDEInterfaceSyntaxDcl assocList::[Pair<WildcardCriteria a>]
{
  return
  if null(assocList)
  then []
  else
    if matchesWildcardCriteria(dcl, fst(head(assocList)))
    then snd(head(assocList)) :: lookupAllMatchingCriteria(dcl, tail(assocList))
    else lookupAllMatchingCriteria(dcl, tail(assocList));
}

