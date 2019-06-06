grammar concretesyntax;

synthesized attribute atomColorString :: String;
nonterminal ColorName with unparse, atomColorString;

concrete production colorPredefinedAtomName
top::ColorName ::= atomColor::AtomName_c
{
  top.unparse = atomColor.unparse;
  top.atomColorString = atomColor.unparse;
}

concrete production blackColorName
top::ColorName ::= 'black'
{
  top.unparse = "black";
  top.atomColorString = "demo-black";
}

concrete production blueColorName
top::ColorName ::= 'blue'
{
  top.unparse = "blue";
  top.atomColorString = "demo-blue";
}

concrete production darkGreenColorName
top::ColorName ::= 'dark-green'
{
  top.unparse = "dark-green";
  top.atomColorString = "demo-dark-green";
}

concrete production greenColorName
top::ColorName ::= 'green'
{
  top.unparse = "green";
  top.atomColorString = "demo-green";
}

concrete production purpleColorName
top::ColorName ::= 'purple'
{
  top.unparse = "purple";
  top.atomColorString = "demo-purple";
}

concrete production redColorName
top::ColorName ::= 'red'
{
  top.unparse = "red";
  top.atomColorString = "demo-red";
}

concrete production whiteColorName
top::ColorName ::= 'white'
{
  top.unparse = "white";
  top.atomColorString = "demo-white";
}
