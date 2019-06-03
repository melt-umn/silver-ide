grammar concretesyntax;

nonterminal ExclusionDeclaration with unparse, grammarName, excludeRules;
nonterminal ExclusionElementList with unparse, grammarName, excludeRules;
nonterminal ExclusionElement with unparse, grammarName, excludeRule;

concrete production exclusionDcl
top::ExclusionDeclaration ::= 'excluding' '[' elemLst::ExclusionElementList ']'
{
  top.excludeRules = elemLst.excludeRules;
  top.unparse = "excluding [" ++ elemLst.unparse ++ "]";
}

concrete production consExclusionElemList
top::ExclusionElementList ::= elem::ExclusionElement ',' rest::ExclusionElementList
{
  top.excludeRules = consExcludeRules(elem.excludeRule, rest.excludeRules);
  top.unparse = elem.unparse ++ ", " ++ rest.unparse;
}

-- necessary for no need for trailing comma
concrete production singleExclusionElemList
top::ExclusionElementList ::= elem::ExclusionElement 
{
  top.excludeRules = consExcludeRules(elem.excludeRule, nilExcludeRules());
  top.unparse = elem.unparse;
}

concrete production nilExclusionElemList
top::ExclusionElementList ::=
{
  top.excludeRules = nilExcludeRules();
  top.unparse = "";
}

concrete production excludeSyntaxDclElem
top::ExclusionElement ::= id::Name
{
  local fullyQualifiedName :: String = top.grammarName ++ ":" ++ id.name;
  top.excludeRule = excludeByName(fullyQualifiedName);
  top.unparse = id.unparse;
}
