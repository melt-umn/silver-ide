grammar ideinterface;

import silver:extension:treesitter;

synthesized attribute atomScopes :: String occurs on IDEInterfaceSyntax, IDEInterfaceSyntaxDcl;

aspect production nilIDESyntax
top::IDEInterfaceSyntax ::=
{
  top.atomScopes = "";
}

aspect production consIDESyntax
top::IDEInterfaceSyntax ::= dcl::IDEInterfaceSyntaxDcl rest::IDEInterfaceSyntax
{
  top.atomScopes = 
    if stringEq("", dcl.atomScopes)
    then rest.atomScopes
    -- 4 spaces
    else dcl.atomScopes ++ "\n    " ++ rest.atomScopes;
}

aspect production ideSyntaxTerminal
top::IDEInterfaceSyntaxDcl ::= name::String properties::IDEInterfaceTerminalProperties
{
    local atomMarkupMaybe::Maybe<String> = lookupBy(stringEq, name, top.spec.atomMarkups);
    top.atomScopes = 
      if atomMarkupMaybe.isJust 
      then buildAtomScopeText(atomMarkupMaybe.fromJust, name)
      else "";
}

aspect production ideSyntaxLexerClass
top::IDEInterfaceSyntaxDcl ::= name::String terms::[String]
{
  local atomMarkupMaybe::Maybe<String> = lookupBy(stringEq, name, top.spec.atomMarkups);
  top.atomScopes = 
    if atomMarkupMaybe.isJust 
    -- 4 spaces
    then implode("\n    ", map(buildAtomScopeText(atomMarkupMaybe.fromJust, _), terms))
    else "";
}

aspect production ideSyntaxNonterminal
top::IDEInterfaceSyntaxDcl ::= name::String subdcls::IDEInterfaceSyntax
{
  top.atomScopes = "";
}

function buildAtomScopeText
String ::= markupName::String terminalName::String
{
  return s"""'${toTsDeclaration(terminalName)}': '${markupName}' """;
}

