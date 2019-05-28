grammar ideinterface;

imports silver:extension:ideinterface;
imports abstractsyntax;

synthesized attribute atomGrammarFile :: String occurs on IDEInterfaceSyntaxRoot;
autocopy attribute spec :: Spec occurs on IDEInterfaceSyntaxRoot, IDEInterfaceSyntax, IDEInterfaceSyntaxDcl;

aspect production ideSyntaxRoot
top::IDEInterfaceSyntaxRoot ::= s::IDEInterfaceSyntax
{
  top.atomGrammarFile = s"""
  name: '${top.spec.langName.fromJust}'
  scopeName: '${top.spec.langName.fromJust}'
  type: 'tree-sitter'
  parser: '${top.spec.treesitterParserName.fromJust}'

  fileTypes: ['${implode(",", top.spec.fileExtensions)}']

  scopes:
    ${s.atomScopes}

  """;
}
