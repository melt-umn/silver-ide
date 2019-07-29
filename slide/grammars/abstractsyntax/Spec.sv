grammar abstractsyntax;

import silver:definition:concrete_syntax:ast;
import silver:definition:env;

-- a list of terminals and there associated atom markup coloring name
synthesized attribute atomMarkups :: [Pair<String String>];
-- a list of terminals that are ignore terminals but should still be highlightable
synthesized attribute highlightableIgnoreTerminals :: [String];

nonterminal Spec with 
  atomMarkups, langName, treesitterParserName, fileExtensions, firstLineRegex
  , highlightableIgnoreTerminals;

abstract production consSpec
top::Spec ::= spec1::Spec spec2::Spec
{
  top.atomMarkups = spec1.atomMarkups ++ spec2.atomMarkups;
  top.langName = orElse(spec1.langName, spec2.langName);
  top.treesitterParserName = orElse(spec1.treesitterParserName, spec2.treesitterParserName);
  top.firstLineRegex = orElse(spec1.firstLineRegex, spec2.firstLineRegex);
  top.fileExtensions = spec1.fileExtensions ++ spec2.fileExtensions;
  top.highlightableIgnoreTerminals = spec1.highlightableIgnoreTerminals ++ spec2.highlightableIgnoreTerminals;
}

abstract production nilSpec
top::Spec ::=
{
  top.atomMarkups = [];
  top.langName = nothing();
  top.treesitterParserName = nothing();
  top.firstLineRegex = nothing();
  top.fileExtensions = [];
  top.highlightableIgnoreTerminals = [];
}

aspect default production
top::Spec ::=
{
  top.atomMarkups = [];
  top.langName = nothing();
  top.treesitterParserName = nothing();
  top.firstLineRegex = nothing();
  top.fileExtensions = [];
  top.highlightableIgnoreTerminals = [];
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
  top.firstLineRegex = properties.firstLineRegex;
  
  top.atomMarkups = properties.grammarWideGlobalSpecs.atomMarkups ++ properties.prefixSpecs.atomMarkups;
  top.atomWildcardMarkups = properties.grammarWideGlobalSpecs.atomWildcardMarkups;
}

abstract production specTerminal
top::Spec ::= name::String properties::SpecTerminalProperties
{
  top.atomMarkups = 
    if properties.atomMarkupName.isJust then 
      [pair(name, properties.atomMarkupName.fromJust)]
    else
      [];

  top.highlightableIgnoreTerminals =
    if properties.ignoreHighlightable then
      [name]
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

abstract production specPrefix
top::Spec ::= name::String properties::SpecPrefixProperties
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

