grammar ideinterface;

import silver:extension:treesitter;

synthesized attribute atomScopes :: String occurs on IDEInterfaceSyntax, IDEInterfaceSyntaxDcl;
synthesized attribute dclName :: String occurs on IDEInterfaceSyntaxDcl;

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
    top.dclName = name;
    local prefixMarkup :: Maybe<String> =
      if properties.isPrefix then
        lookupBy(stringEq, properties.regexString, top.spec.atomMarkups)
      else
        nothing();

    -- check if any direct declarations were made
    local atomMarkupMaybe::Maybe<String> = lookupBy(stringEq, name, top.spec.atomMarkups);
    -- check if it satisfies any wildcards 
    local atomWildcardMarkup :: [String] = lookupAllMatchingCriteria(top, top.spec.atomWildcardMarkups);
    top.atomScopes = 
      if prefixMarkup.isJust 
      then buildAtomScopeText(prefixMarkup.fromJust, name)
      else if atomMarkupMaybe.isJust 
      then buildAtomScopeText(atomMarkupMaybe.fromJust, name)
      else if !null(atomWildcardMarkup)
      then buildAtomScopeText(head(atomWildcardMarkup), name)
      else "";
}

aspect production ideSyntaxLexerClass
top::IDEInterfaceSyntaxDcl ::= name::String terms::[String]
{
  top.dclName = name;
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
  top.dclName = name;
  -- check if any direct declarations were made
  local atomMarkupMaybe::Maybe<String> = lookupBy(stringEq, name, top.spec.atomMarkups);
  -- check if it satisfies any wildcards 
  local atomWildcardMarkup :: [String] = lookupAllMatchingCriteria(top, top.spec.atomWildcardMarkups);
  top.atomScopes = 
    if atomMarkupMaybe.isJust
    -- need 4 spaces
    then buildAtomScopeText(atomMarkupMaybe.fromJust, name)
    -- take the first one for now eventually include specificity of wildcards and choose most specific.
    else if !null(atomWildcardMarkup)
    then buildAtomScopeText(head(atomWildcardMarkup), name)
    else "";
}

function buildAtomScopeText
String ::= markupName::String terminalName::String
{
  return s"""'${toTsDeclaration(terminalName)}': '${markupName}' """;
}

function isTerminal
Boolean ::= dcl::IDEInterfaceSyntaxDcl
{
  return case dcl of
  | ideSyntaxTerminal(_, _) -> true
  | _ -> false
  end;
}

function isNonterminal
Boolean ::= dcl::IDEInterfaceSyntaxDcl
{
  return case dcl of
  | ideSyntaxNonterminal(_, _) -> true
  | _ -> false
  end;
}
