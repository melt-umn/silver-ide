grammar concretesyntax;

concrete production nilSpecComponent
top::SpecComponents ::=
{
  top.unparse = "";
  top.specification = nilSpec();
}

concrete production consSpecComponent
top::SpecComponents ::= comp::SpecComponent ';' rest::SpecComponents
{
  top.unparse = comp.unparse ++ ";" ++ rest.unparse;
  top.specification = consSpec(comp.specification, rest.specification);
}

concrete production specComponentTerminal
top::SpecComponent ::= 'terminal' id::Name '{' props::TerminalProperties_c '}'
{
  top.unparse = "terminal " ++ id.unparse ++ " {" ++ props.unparse ++ "}";
  local fullName :: String = top.grammarName ++ ":" ++ id.name;
  top.specification = specTerminal(fullName, props.terminalProperties);
}

concrete production specComponentLexerClass
top::SpecComponent ::= 'lexer' cls::Class_kwd id::Name '{' props::LexerClassProperties_c '}'
{
  top.unparse = "lexer class " ++ id.unparse ++ " {" ++ props.unparse ++ "}";
  local fullName :: String = top.grammarName ++ ":" ++ id.name;
  top.specification = specLexerClass(fullName, props.lexerClassProperties);
}

concrete production specComponentNonterminal
top::SpecComponent ::= 'nonterminal' id::Name '{' props::NonterminalProperties_c '}'
{
  top.unparse = "nonterminal " ++ id.unparse ++ " {" ++ props.unparse ++ "}";
  local fullName :: String = top.grammarName ++ ":" ++ id.name;
  top.specification = specNonterminal(fullName, props.nonterminalProperties);
}

{-- Wildcard Spec Components --}
concrete production specComponentWildcardNonterminal
top::SpecComponent ::= 'nonterminal' wcDcl::WildcardDcl '{' props::NonterminalProperties_c '}'
{
  top.unparse = "nonterminal " ++ wcDcl.unparse ++ " {" ++ props.unparse ++ "}";
  top.specification = specWildcardNonterminal(wcDcl.wcCriteria, props.nonterminalProperties);
}

concrete production specComponentWildcardTerminal
top::SpecComponent ::= 'terminal' wcDcl::WildcardDcl '{' props::TerminalProperties_c '}'
{
  top.unparse = "terminal " ++ wcDcl.unparse ++ " {" ++ props.unparse ++ "}";
  top.specification = specWildcardTerminal(wcDcl.wcCriteria, props.terminalProperties);
}
