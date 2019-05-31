grammar abstractsyntax;

{--
 - Modifiers for terminals.
 -}
synthesized attribute highlightable :: Boolean;

nonterminal SpecTerminalProperties with 
  atomMarkupName, highlightable;
nonterminal SpecTerminalProperty with 
  atomMarkupName, highlightable;

abstract production consTerminalProp
top::SpecTerminalProperties ::= h::SpecTerminalProperty  t::SpecTerminalProperties
{
  -- pick first declaration seen
  top.atomMarkupName = orElse(t.atomMarkupName, h.atomMarkupName);
  top.highlightable = h.highlightable || t.highlightable;
}

abstract production nilTerminalProp
top::SpecTerminalProperties ::=
{
  top.atomMarkupName = nothing();
  top.highlightable = false;
}


{- We default ALL attributes, so we can focus only on those that are interesting in each case... -}
aspect default production
top::SpecTerminalProperty ::=
{
  top.atomMarkupName = nothing();
  top.highlightable = false;
}

abstract production atomMarkupNamePropTerminal
top::SpecTerminalProperty ::= name::String
{
  top.atomMarkupName = just(name);
}

abstract production highlightablePropTerminal
top::SpecTerminalProperty ::=
{
  top.highlightable = true;
}
