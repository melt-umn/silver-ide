Parallel Tree Search Demo
------
This example shows you how to use Treesitter highlighting for ableC and set of extensions. The first things to do is make sure everything is built and up to date.

1. Pull the latest version of Silver and checkout the branch `feature/treesitter`. Recompile with the `./self-compile` then `cp build/silver.composed.Default.jar jars/` to move the Silver jar to be the one used on your system. This is necessary since this code is not entirely complete so it has not been merged into the released version of jar.
2. Pull the latest version of [Silver IDE tools](https://github.com/melt-umn/silver-ide). Run the `./build-everything` script. This should build and install everything you need to run all the tools. Make sure you have `~/bin` in your path. This should be there from Silver but double check if there are any issues.
3. Pull the latest version of ableC and checkout the `feature/ide_support` branch.
4. This example is meant to work with the `ableC-sample-projects` repository and specifically the `parallel_tree_search`. Make sure you have all the extensions needed for this example and pull the latest versions to get the new branch for ide support.
  - ableC-cilk (check out the `feature/ide-support` branch)
  - ableC-regex-lib (check out the `feature/ide-support` branch)
  -  ableC-algebraic-data-types (check out the `feature/ide-support` branch)
  -  ableC-regex-pattern-matching (This does not have any new terminals/nonterminals so there is no ide specification. It does not have the `feature/ide-support` branch)
5. [Create an account on NPM](https://www.npmjs.com/signup) if you do not have one already. Either way make sure you know your username and password.

Once you have all the previous steps, you are ready to test out the example. To do this go into the new `ableC-sample-projects/parallel_tree_search` directory.

There are 4 make commands you can run
1. `make atom-ide-demo`: This is meant for first time use to build the Treesitter parser and Atom highlighting for demo modes. This only needs to be run once.
2. `make update-spec-demo`: This should be used if you change the specification files with the `.demo` extension to rebuild the Atom highlighting package. This should only be done after you have already run `make atom-ide-demo`
3. `make atom-ide`: This is meant for first time use to build the Treesitter parser and Atom highlighting for the language without any special highlighting designed for demos.
4. `make update-spec`: This should be used if you change the specification files with the `.slide` extension after you have already run `make atom-ide`.

After this, you should be able to open Atom and `ptsDemo.xc` should have demo highlighting if you have run `make atom-ide-demo` and `pts.xc` should have regular highlighting if you have run `make atom-ide`.
