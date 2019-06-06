grammar concretesyntax;

--autocopy attribute grammarName :: String;

-- a flag indicating that this quick spec is for the specified grammar name
-- and all the subgrammars below it
inherited attribute subGrammarsSpecToo :: Boolean;

nonterminal GrammarWideSpecDcl
  with unparse, specification;

concrete production grammarWideSpecDcl
top::GrammarWideSpecDcl ::= 'grammar' gramName::QName spec::QuickSpec
{
  spec.grammarName = gramName.name;
  spec.subGrammarsSpecToo = false;
  top.unparse = "grammar " ++ gramName.unparse ++ spec.unparse;
  top.specification = spec.specification;
}

concrete production grammarsWideSpecDcl
top::GrammarWideSpecDcl ::= 'grammars' 'under' gramName::QName spec::QuickSpec
{
  spec.grammarName = gramName.name;
  spec.subGrammarsSpecToo = true;
  top.unparse = "grammars under " ++ gramName.unparse ++ spec.unparse;
  top.specification = spec.specification;
}

nonterminal QuickSpec
  with unparse, specification, grammarName, subGrammarsSpecToo;

concrete production colorNonterminalsQuickSpec
top::QuickSpec ::= 'color' 'nonterminals' name::ColorName
{
  local attribute grammarWcCriteriaElem :: WildcardCriteriaElement =
    if top.subGrammarsSpecToo
    then grammarsUnderWildcardCriteriaElem(top.grammarName)
    else grammarWildcardCriteriaElem(top.grammarName);

  local attribute wildcardCriteria :: WildcardCriteria =
    consWildcardCriteria(grammarWcCriteriaElem, nilWildcardCriteria());

  local attribute ntProperties :: SpecNonterminalProperties =
    consNonterminalProp(atomMarkupNamePropNonterminal(name.atomColorString), nilNonterminalProp());

  top.specification = specWildcardNonterminal(wildcardCriteria, ntProperties);
  top.unparse = "color nonterminals " ++ name.unparse;
}

concrete production colorNonterminalQuickSpecWithExclude
top::QuickSpec ::= 'color' 'nonterminals' name::ColorName excludeDcl::ExclusionDeclaration
{
  local attribute grammarWcCriteriaElem :: WildcardCriteriaElement =
    if top.subGrammarsSpecToo
    then grammarsUnderWildcardCriteriaElem(top.grammarName)
    else grammarWildcardCriteriaElem(top.grammarName);

  local attribute wildcardCriteria :: WildcardCriteria =
    consWildcardCriteria(
      grammarWcCriteriaElem,
    consWildcardCriteria(
      excludeRulesWildcardCriteriaElem(excludeDcl.excludeRules),
    nilWildcardCriteria()));

  local attribute ntProperties :: SpecNonterminalProperties =
    consNonterminalProp(atomMarkupNamePropNonterminal(name.atomColorString), nilNonterminalProp());

  top.specification = specWildcardNonterminal(wildcardCriteria, ntProperties);
  top.unparse = "color nonterminals " ++ name.unparse;
}

concrete production colorTerminalsQuickSpec
top::QuickSpec ::= 'color' 'terminals' name::ColorName
{
  local attribute grammarWcCriteriaElem :: WildcardCriteriaElement =
    if top.subGrammarsSpecToo
    then grammarsUnderWildcardCriteriaElem(top.grammarName)
    else grammarWildcardCriteriaElem(top.grammarName);

  local attribute wildcardCriteria :: WildcardCriteria =
    consWildcardCriteria(grammarWcCriteriaElem, nilWildcardCriteria());

  local attribute termProperties :: SpecTerminalProperties =
    consTerminalProp(atomMarkupNamePropTerminal(name.atomColorString), nilTerminalProp());

  top.specification = specWildcardTerminal(wildcardCriteria, termProperties);
  top.unparse = "color terminals " ++ name.unparse;
}


concrete production colorTerminalsQuickSpecWithExclude
top::QuickSpec ::= 'color' 'terminals' name::ColorName excludeDcl::ExclusionDeclaration
{
  local attribute grammarWcCriteriaElem :: WildcardCriteriaElement =
    if top.subGrammarsSpecToo
    then grammarsUnderWildcardCriteriaElem(top.grammarName)
    else grammarWildcardCriteriaElem(top.grammarName);

  local attribute wildcardCriteria :: WildcardCriteria =
    consWildcardCriteria(
      grammarWcCriteriaElem,
    consWildcardCriteria(
      excludeRulesWildcardCriteriaElem(excludeDcl.excludeRules),
    nilWildcardCriteria()));

  local attribute termProperties :: SpecTerminalProperties =
    consTerminalProp(atomMarkupNamePropTerminal(name.atomColorString), nilTerminalProp());

  top.specification = specWildcardTerminal(wildcardCriteria, termProperties);
  top.unparse = "color terminals " ++ name.unparse;
}
