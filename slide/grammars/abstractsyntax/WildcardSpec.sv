grammar abstractsyntax;

synthesized attribute atomWildcardMarkups :: [Pair<WildcardCriteria String>]
  occurs on Spec;

abstract production specWildcardNonterminal
top::Spec ::= m::WildcardCriteria properties::SpecNonterminalProperties
{
  local attribute ntOnlyCriteria :: WildcardCriteria = consWildcardCriteria(nonterminalOnlyWildcardCriteriaElem(), m);

  top.atomWildcardMarkups =
    if properties.atomMarkupName.isJust then
      [pair(ntOnlyCriteria, properties.atomMarkupName.fromJust)]
    else
      [];
}

aspect production nilSpec
top::Spec ::=
{
  top.atomWildcardMarkups = [];
}

aspect default production
top::Spec ::=
{
  top.atomWildcardMarkups = [];
}

aspect production consSpec
top::Spec ::= spec1::Spec spec2::Spec
{
  top.atomWildcardMarkups = spec1.atomWildcardMarkups ++ spec2.atomWildcardMarkups;
}

