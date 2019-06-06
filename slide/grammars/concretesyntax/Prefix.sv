grammar concretesyntax;

synthesized attribute prefixProperties :: SpecPrefixProperties;
synthesized attribute prefixProperty :: SpecPrefixProperty;

nonterminal PrefixSpecDcl with unparse, specification;
nonterminal PrefixProperties_c with unparse, prefixProperties;
nonterminal PrefixProperty_c with unparse, prefixProperty;

concrete production prefixSpecification
top::PrefixSpecDcl ::= 'prefix' '"' p::Name '"' '{' props::PrefixProperties_c '}'
{
  local attribute prefixSepDefault :: String = "::";
  top.unparse = "prefix \"" ++ p.unparse ++ "\" { " ++ props.unparse ++ "}";
  top.specification = specPrefix(p.name ++ prefixSepDefault, props.prefixProperties);
}

concrete production nilPrefixProperties_c
top::PrefixProperties_c ::=
{
  top.unparse = "";
  top.prefixProperties = nilPrefixProp();
}

concrete production consPrefixProperties_c
top::PrefixProperties_c ::= prop::PrefixProperty_c ';' rest::PrefixProperties_c 
{
  top.unparse = prop.unparse ++ ";" ++ rest.unparse;
  top.prefixProperties = consPrefixProp(prop.prefixProperty, rest.prefixProperties);
}

concrete production atomMarkupNamePropPre
top::PrefixProperty_c ::= 'atomMarkupName' '=' name::AtomName_c
{
  top.unparse = "atomMarkupName = " ++ name.unparse;
  top.prefixProperty = atomMarkupNamePropPrefix(name.unparse);
}
