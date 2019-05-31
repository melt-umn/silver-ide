grammar concretesyntax;

-- given spec files, get a spec root
synthesized attribute specification :: Spec;
-- child specifications. These are used to provide specification for nonterminals
-- or terminals when they are the children of some nonterminal.
{--
synthesized attribute childSpecs :: [Spec];
inherited attribute parentName :: String;
--}
autocopy attribute grammarName :: String;

nonterminal Specification with specification, unparse;
nonterminal SpecRoot with specification, unparse;
nonterminal SpecComponents with 
  grammarName, specification, unparse;
nonterminal SpecComponent with 
  grammarName, specification, unparse;

concrete production nilSpecRoot
top::SpecRoot ::=
{
  top.specification = nilSpec();
  top.unparse = "";
}

concrete production consSpecRoot
top::SpecRoot ::= spec1::Specification spec2::SpecRoot
{
  top.specification = consSpec(spec1.specification, spec2.specification);
  top.unparse = spec1.unparse ++ ",\n" ++ spec2.unparse;
}

concrete production globalSpecificationDcl
top::Specification ::= 'global' 'ide_specification' '{' langProps::LanguageProperties_c '}'
{
  top.specification = globalSpec(langProps.langProperties);
  top.unparse = "global ide_specification { " ++ langProps.unparse ++ "}";
}
concrete production specificationDcl
top::Specification ::= 'ide_specification' 'for' gramName::QName '{' s::SpecComponents '}'
{
  top.specification = s.specification; --cstSpecRoot(langProps.langProperties, s.spec);
  top.unparse = "ide_specification for " ++ gramName.unparse ++ "{" ++ s.unparse ++ "}";
  s.grammarName = gramName.name;
}

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
  top.unparse = "terminal" ++ id.unparse ++ "{" ++ props.unparse ++ "}";
  local fullName :: String = top.grammarName ++ ":" ++ id.name;
  top.specification = specTerminal(fullName, props.terminalProperties);
}

concrete production specComponentLexerClass
top::SpecComponent ::= 'lexer' cls::Class_kwd id::Name '{' props::LexerClassProperties_c '}'
{
  top.unparse = "lexer class" ++ id.unparse ++ "{" ++ props.unparse ++ "}";
  local fullName :: String = top.grammarName ++ ":" ++ id.name;
  top.specification = specLexerClass(fullName, props.lexerClassProperties);
}

concrete production specComponentNonterminal
top::SpecComponent ::= 'nonterminal' id::Name '{' props::NonterminalProperties_c '}'
{
  top.unparse = "nonterminal" ++ id.unparse ++ "{" ++ props.unparse ++ "}";
  local fullName :: String = top.grammarName ++ ":" ++ id.name;
  top.specification = specNonterminal(fullName, props.nonterminalProperties);
}
