grammar abstractsyntax;

nonterminal SpecPrefixProperties with
  atomMarkupName;

nonterminal SpecPrefixProperty with
  atomMarkupName;

abstract production consPrefixProp
top::SpecPrefixProperties ::= h::SpecPrefixProperty t::SpecPrefixProperties
{
  top.atomMarkupName = orElse(h.atomMarkupName, t.atomMarkupName);
}

abstract production nilPrefixProp
top::SpecPrefixProperties ::=
{
  top.atomMarkupName = nothing();
}

{- We default ALL attributes, so we can focus only on those that are interesting in each case... -}
aspect default production
top::SpecPrefixProperty ::= 
{
  top.atomMarkupName = nothing();
}

abstract production atomMarkupNamePropPrefix
top::SpecPrefixProperty ::= name::String
{
  top.atomMarkupName = just(name);
}
