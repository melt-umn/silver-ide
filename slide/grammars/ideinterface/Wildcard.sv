
function matchesWildcardCriteria
Boolean ::= dcl::IDEInterfaceSyntaxDcl wc::WildcardCriteria 
{
  local attribute grammarName :: String = substring(0, lastIndexOf(":", dcl.dclName), dcl.dclName);
  local attribute grammarMatch :: Boolean =
    if wc.requiredGrammar.isJust -- check if it needs to be and is a direct match
    then stringEq(wc.requiredGrammar.fromJust, grammarName)
    else if wc.requiredUnderGrammar.isJust -- check if it needs to be and is in this grammar or a subgrammar
    then startsWith(wc.requiredUnderGrammar.fromJust, grammarName)
    else true; -- otherwise no grammar requirements are imposed so this trivially matches

  local attribute correctDclType :: Boolean =
    (wc.mustBeTerminal && isTerminal(dcl)) -- it must be a terminal and is
    || (wc.mustBeNonterminal && isNonterminal(dcl)) -- or it must be a terminal and is
    || (!wc.mustBeTerminal && !wc.mustBeNonterminal); -- or it does not need to be a terminal or nonterminal

  local attribute isNotExcluded :: Boolean = !shouldBeExcluded(dcl, wc.excludeRules);

  return correctDclType && grammarMatch && isNotExcluded;
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

function shouldBeExcluded
Boolean ::= dcl::IDEInterfaceSyntaxDcl rules::ExcludeRules 
{
  local attribute tests :: [(Boolean ::= IDEInterfaceSyntaxDcl)] = rules.excludeTests;
  -- if any of these tests return true the dcl should be excluded
  local attribute results :: [Boolean] = map(runExcludeTest(dcl, _), tests);
  return any(results);
}

function runExcludeTest
Boolean ::= dcl::IDEInterfaceSyntaxDcl test::(Boolean ::= IDEInterfaceSyntaxDcl) 
{
  return test(dcl);
}
