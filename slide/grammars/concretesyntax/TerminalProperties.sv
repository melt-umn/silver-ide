grammar concretesyntax;

synthesized attribute terminalProperties :: SpecTerminalProperties;
synthesized attribute terminalProperty :: SpecTerminalProperty;
nonterminal TerminalProperties_c with unparse, terminalProperties;
nonterminal TerminalProperty_c with unparse, terminalProperty;

concrete production nilTerminalProperties_c
top::TerminalProperties_c ::=
{
  top.unparse = "";
  top.terminalProperties = nilTerminalProp();
}

concrete production consTerminalProperties_c
top::TerminalProperties_c ::= prop::TerminalProperty_c ';' rest::TerminalProperties_c
{
  top.unparse = prop.unparse ++ ";" ++ rest.unparse;
  top.terminalProperties = consTerminalProp(prop.terminalProperty, rest.terminalProperties);
}

concrete production atomMarkupNamePropTerm
top::TerminalProperty_c ::= 'atomMarkupName' '=' name::AtomName_c
{
  top.unparse = "atomMarkupName = " ++ name.unparse;
  top.terminalProperty = atomMarkupNamePropTerminal(name.unparse);
}

concrete production highlightableProp
top::TerminalProperty_c ::= 'highlightable'
{
  top.unparse = "highlightable";
  top.terminalProperty = highlightablePropTerminal();
}
