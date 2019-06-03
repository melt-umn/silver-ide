grammar abstractsyntax;

import silver:extension:ideinterface;
import ideinterface;
{--
  This file represents the abstract syntax of exclude rules. Exclude rules can
  occur on WildcardCriteria that specify that a wildcard match should be
  excluded even though it matches the criteria. An exclusion rule is meant
  to have the form Boolean ::= SyntaxDcl. Thus, after checking if a wildcard
  criteria matches we can then check if any of the exclude rules return true
  indicating the declaration should be excluded.
--}

synthesized attribute excludeTests :: [(Boolean ::= IDEInterfaceSyntaxDcl)];
nonterminal ExcludeRules with excludeTests;
nonterminal ExcludeRule with excludeTests;

abstract production nilExcludeRules
top::ExcludeRules ::=
{
  top.excludeTests = [];
}

abstract production consExcludeRules
top::ExcludeRules ::= rule::ExcludeRule rest::ExcludeRules
{
  top.excludeTests = rule.excludeTests ++ rest.excludeTests;
}

abstract production excludeByName
top::ExcludeRule ::= name::String
{
  top.excludeTests = [syntaxDclHasName(name, _)];
}

function appendExcludeRules
ExcludeRules ::= ruleset1::ExcludeRules ruleset2::ExcludeRules
{
  return
  case ruleset1 of
  | nilExcludeRules() -> ruleset2
  | consExcludeRules(rule, rest) -> appendExcludeRules(rest, 
      consExcludeRules(rule, ruleset2))
  end;
}

{--
  Functions used as exclude tests
--}

function syntaxDclHasName
Boolean ::= name::String dcl::IDEInterfaceSyntaxDcl
{
  return stringEq(name, dcl.dclName);
}
