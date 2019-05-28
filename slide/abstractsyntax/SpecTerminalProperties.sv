grammar slide:abstractsyntax;

{--
 - Modifiers for terminals.
 -}
nonterminal SpecTerminalProperties with atomMarkupName;
nonterminal SpecTerminalProperty with atomMarkupName;

abstract production consTerminalProp
top::SpecTerminalProperties ::= h::SpecTerminalProperty  t::SpecTerminalProperties
{
  -- pick first declaration seen
  top.atomMarkupName = orElse(t.atomMarkupName, h.atomMarkupName);
}

abstract production nilTerminalProp
top::SpecTerminalProperties ::=
{
  top.atomMarkupName = nothing();
}


{- We default ALL attributes, so we can focus only on those that are interesting in each case... -}
aspect default production
top::SpecTerminalProperty ::=
{
  top.atomMarkupName = nothing();
}

abstract production atomMarkupNamePropTerminal
top::SpecTerminalProperty ::= name::String
{
  top.atomMarkupName = just(name);
}
