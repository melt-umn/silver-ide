grammar ideinterface;

imports silver:extension:ideinterface;
imports concretesyntax:regex only regexString;
imports abstractsyntax;

synthesized attribute atomGrammarFile :: String occurs on IDEInterfaceSyntaxRoot;
autocopy attribute spec :: Spec occurs on IDEInterfaceSyntaxRoot, IDEInterfaceSyntax, IDEInterfaceSyntaxDcl;

aspect production ideSyntaxRoot
top::IDEInterfaceSyntaxRoot ::= s::IDEInterfaceSyntax
{
  local firstLineRegex :: String = 
    if top.spec.firstLineRegex.isJust then
      s"""firstLineRegex: ['${top.spec.firstLineRegex.fromJust.regexString}']"""
    else
      "";

  top.atomGrammarFile = s"""
  name: '${top.spec.langName.fromJust}'
  scopeName: '${top.spec.langName.fromJust}'
  type: 'tree-sitter'
  parser: '${top.spec.treesitterParserName.fromJust}'

  fileTypes: ['${implode(",", top.spec.fileExtensions)}']
  ${firstLineRegex}

  scopes:
    ${s.atomScopes}

  """;
}
