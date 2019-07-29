grammar abstractsyntax;

{--
 - Modifiers for terminals.
 -}
 -- this specifies that even though this is an ignore terminal in Silver it 
 -- should still appear in whatever is used to provide highlighting so that
 -- it can be highlighted. For example, for treesitter these terminals should
 -- appear in the CST.
synthesized attribute ignoreHighlightable :: Boolean;

nonterminal SpecTerminalProperties with 
  atomMarkupName, ignoreHighlightable;
nonterminal SpecTerminalProperty with 
  atomMarkupName, ignoreHighlightable;

abstract production consTerminalProp
top::SpecTerminalProperties ::= h::SpecTerminalProperty  t::SpecTerminalProperties
{
  -- pick first declaration seen
  top.atomMarkupName = orElse(t.atomMarkupName, h.atomMarkupName);
  top.ignoreHighlightable = h.ignoreHighlightable || t.ignoreHighlightable;
}

abstract production nilTerminalProp
top::SpecTerminalProperties ::=
{
  top.atomMarkupName = nothing();
  top.ignoreHighlightable = false;
}


{- We default ALL attributes, so we can focus only on those that are interesting in each case... -}
aspect default production
top::SpecTerminalProperty ::=
{
  top.atomMarkupName = nothing();
  top.ignoreHighlightable = false;
}

abstract production atomMarkupNamePropTerminal
top::SpecTerminalProperty ::= name::String
{
  top.atomMarkupName = just(name);
}

abstract production highlightablePropTerminal
top::SpecTerminalProperty ::=
{
  top.ignoreHighlightable = true;
}
