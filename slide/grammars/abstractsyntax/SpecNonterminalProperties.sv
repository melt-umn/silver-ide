grammar abstractsyntax;

{--
 - Properties for nonterminals.
 -}
nonterminal SpecNonterminalProperties with atomMarkupName;
nonterminal SpecNonterminalProperty with atomMarkupName;

abstract production consNonterminalProp
top::SpecNonterminalProperties ::= h::SpecNonterminalProperty  t::SpecNonterminalProperties
{
  -- pick first declaration seen
  top.atomMarkupName = orElse(t.atomMarkupName, h.atomMarkupName);
}

abstract production nilNonterminalProp
top::SpecNonterminalProperties ::=
{
  top.atomMarkupName = nothing();
}



{- We default ALL attributes, so we can focus only on those that are interesting in each case... -}
aspect default production
top::SpecNonterminalProperty ::=
{
  top.atomMarkupName = nothing();
}

abstract production atomMarkupNamePropNonterminal
top::SpecNonterminalProperty ::= name::String
{
  top.atomMarkupName = just(name);
}
