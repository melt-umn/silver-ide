grammar concretesyntax;

--autocopy attribute grammarName :: String;


nonterminal GrammarWideSpecDcl
  with unparse, specification;

concrete production grammarWideSpecDcl
top::GrammarWideSpecDcl ::= 'grammar' gramName::QName spec::QuickSpec
{
  spec.grammarName = gramName.name;
  top.unparse = "grammar " ++ gramName.unparse ++ spec.unparse;
  top.specification = spec.specification;
}


nonterminal QuickSpec
  with unparse, specification, grammarName;

concrete production colorNonterminalsQuickSpec
top::QuickSpec ::= 'color' 'nonterminals' name::AtomName_c
{
  local attribute wildcardCriteria :: WildcardCriteria = 
    consWildcardCriteria(grammarWildcardCriteriaElem(top.grammarName), nilWildcardCriteria());

  local attribute ntProperties :: SpecNonterminalProperties =
    consNonterminalProp(atomMarkupNamePropNonterminal(name.unparse), nilNonterminalProp());

  top.specification = specWildcardNonterminal(wildcardCriteria, ntProperties);
  top.unparse = "color nonterminals " ++ name.unparse;
}

concrete production colorNonterminalQuickSpecWithExclude
top::QuickSpec ::= 'color' 'nonterminals' name::AtomName_c excludeDcl::ExclusionDeclaration
{
  local attribute wildcardCriteria :: WildcardCriteria = 
    consWildcardCriteria(
      grammarWildcardCriteriaElem(top.grammarName), 
    consWildcardCriteria(
      excludeRulesWildcardCriteriaElem(excludeDcl.excludeRules),
    nilWildcardCriteria()));

  local attribute ntProperties :: SpecNonterminalProperties =
    consNonterminalProp(atomMarkupNamePropNonterminal(name.unparse), nilNonterminalProp());

  top.specification = specWildcardNonterminal(wildcardCriteria, ntProperties);
  top.unparse = "color nonterminals " ++ name.unparse;
}
