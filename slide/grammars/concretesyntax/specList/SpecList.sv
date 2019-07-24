import concretesyntax only unparse;

nonterminal SpecNameList with specNames, unparse;
terminal Id_t /[A-Za-z][A-Za-z0-9\_]*/;
terminal Semi_t ';';
ignore terminal WhiteSpace /[\t\r\n\ ]+/;
synthesized attribute specNames :: [String];

concrete production consSpecList
top::SpecNameList ::= name::Id_t ';' rest::SpecNameList
{
  top.unparse = name.lexeme ++ ";\n" ++ rest.unparse;
  top.specNames = name.lexeme :: rest.specNames;
}

concrete production nilSpecList
top::SpecNameList ::= 
{
  top.specNames = [];
  top.unparse = "";
}

