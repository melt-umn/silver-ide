grammar slide:abstractsyntax;

import silver:definition:concrete_syntax:ast;
import silver:definition:env;

-- a list of terminals and there associated atom markup coloring name
synthesized attribute atomMarkups :: [Pair<String String>];

nonterminal Spec with atomMarkups, langName, treesitterParserName, 
  fileExtensions;

abstract production consSpec
top::Spec ::= spec1::Spec spec2::Spec
{
  top.atomMarkups = spec1.atomMarkups ++ spec2.atomMarkups;
  top.langName = orElse(spec1.langName, spec2.langName);
  top.treesitterParserName = orElse(spec1.treesitterParserName, spec2.treesitterParserName);
  top.fileExtensions = spec1.fileExtensions ++ spec2.fileExtensions;
}

abstract production nilSpec
top::Spec ::=
{
  top.atomMarkups = [];
  top.langName = nothing();
  top.treesitterParserName = nothing();
  top.fileExtensions = [];
}

aspect default production
top::Spec ::=
{
  top.atomMarkups = [];
  top.langName = nothing();
  top.treesitterParserName = nothing();
  top.fileExtensions = [];
}

abstract production errorSpec
top::Spec ::= errs::[String] dirs::[String]
{
  forwards to nilSpec();
}

abstract production globalSpec
top::Spec ::= properties::LanguageProperties
{
  top.langName = properties.langName;
  top.treesitterParserName = properties.treesitterParserName;
  top.fileExtensions = properties.fileExtensions;
}

abstract production specTerminal
top::Spec ::= name::String properties::SpecTerminalProperties
{
  top.atomMarkups = 
    if properties.atomMarkupName.isJust then 
      [pair(name, properties.atomMarkupName.fromJust)]
    else
      [];
}

abstract production specLexerClass
top::Spec ::= name::String properties::SpecLexerClassProperties
{
  top.atomMarkups = 
    if properties.atomMarkupName.isJust then 
      [pair(name, properties.atomMarkupName.fromJust)]
    else
      [];
}

abstract production specNonterminal
top::Spec ::= name::String properties::SpecNonterminalProperties
{
  top.atomMarkups =
    if properties.atomMarkupName.isJust then
      [pair(name, properties.atomMarkupName.fromJust)]
    else
      [];
}

function buildAtomMarkups
Pair<String String> ::= atomMarkupName::String term::String
{
  return pair(term, atomMarkupName);
}

function appendSpecs
Spec ::= spec1::Spec spec2::Spec
{
  return
  case spec1 of
  | nilSpec() -> spec2
  | consSpec(spec, rest) -> consSpec(spec, appendSpecs(rest, spec2))
  | errorSpec(errs, dirs) ->
      case spec2 of
      -- combine errors
      | errorSpec(moreErrs, moreDirs) -> 
          errorSpec(append(errs, moreErrs), append(dirs, moreDirs))
      -- propogate errors up 
      | _ -> spec1
      end
  end;
}

