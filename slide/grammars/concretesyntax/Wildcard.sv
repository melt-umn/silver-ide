grammar concretesyntax;

synthesized attribute wcCriteria :: WildcardCriteria;
nonterminal WildcardDcl with grammarName, wcCriteria, unparse;

concrete production wildcardMatchAllInGrammar
top::WildcardDcl ::= '*'
{
  top.unparse = "*";
  top.wcCriteria = 
    consWildcardCriteria(grammarWildcardCriteriaElem(top.grammarName), nilWildcardCriteria());
}

concrete production wildcardMathAllInGrammarWithExclusions
top::WildcardDcl ::= '*' exclusions::ExclusionDeclaration
{
  top.unparse = "* " ++ exclusions.unparse;
  top.wcCriteria = 
    consWildcardCriteria(
      grammarWildcardCriteriaElem(top.grammarName), 
    consWildcardCriteria(
      excludeRulesWildcardCriteriaElem(exclusions.excludeRules),
    nilWildcardCriteria()));
}
