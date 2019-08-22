Adding Language Server Protocol Support To A Language Written in Silver
-----------------------------------------------------------------------
To add language server protocol support to a language written in Silver, we
will make heavy use of the lsp library provided by Silver. To understand how
this library works we first need to understand how the Language Server Protocol
works. The Language Server Protocol (LSP) is a protocol used by IDEs to
communicate with language servers which provide IDE language feature support.
The LSP uses JSON Remote Procedure Call (RPC) to communicate back and forth. JSON
RPC uses methods to communicate what the client or server should do.
JSON RPC defines two types of methods
1. Requests which have an id, method and parameters and require a response with
the same id and either a result or error.
2. Notifications which contain a method and parameters and do not have a
response.

#### Request and Notification Handlers and the LSP Interface

The Silver LSP library handles all of the communication with the client for you.
You just need to tell the LSP library what Silver function to call when a method
is received from the client. This is done by aspecting the function
`buildInterface` and adding your `RequestHandler`s and `NotificationHandler`s
to the production attributes

`requestHandlers::[RequestHandler] with ++` and

`notificationHandlers::[NotificationHandler] with ++`

At this point you are probably wondering what are RequestHandlers and
NotificationHandlers. This type is effectively a wrapper union type for all of
the different requests and notifications that can exist.

All requests handler productions take in a function that is an
 `LSPRequestFunction<a b>`
which is a type variable for `Pair<State b> ::= State a IO`. This states that
every `LSPRequestFunction` takes in the current state of the server,
some structured input of type `a` and an IO token and needs to return a pair of
the updated state and a structured ouput type of type `b`. For example, the
production `hoverRequestHandler` has signature
`top::RequestHandler ::= func::LSPRequestFunction<HoverRequest HoverResult>`
where `HoverRequest` is the structured input for a hover request as defined by
the Language Server Protocol and `HoverResult` is a wrapper of one of the return
options defined by the protocol. Thus, a function that provides hover information
through the LSP protocol must have the signature
`Pair<State HoverResult> ::= State HoverRequest IO`. If this function is called
`hoverHandler`, then this is added to the callable functions by adding it to
the request handlers with code

```
aspect function buildInterface
LSP_Interface ::=
{
  requestHandlers <- [hoverRequestHandler(hoverHandler),
                      other request handlers...];
  notificationHandlers <- [notification handlers...];
}
```
Similar code would be added for the other request and notification handler
functions written for your language.

All notification handler productions take in a function that is an
`LSPNotificationFunction<a>` which is a type variable for
`State ::= State a IO`. This states that every `LSPNotificationFunction` takes
in the current state of the server, some structured input of type `a` and an IO
token and returns the updated state. For example, the production
`didChangeNotificationHandler` has signature

`top::NotificationHandler ::= func::LSPNotificationFunction<DidChangeTextDocumentNotification>`

where `DidChangeTextDocumentNotification` is the structured input of change
notifications as defined by the LSP.

#### State
The next thing to discuss is this `State` that is passed into all request and
notification handlers. The Language Server Protocol is stateful indicating the
server can keep information around that is useful between calls. However, Silver
is stateless. Thus, we must explicitly pass and update the state in every request
and notification handlers.

In the Silver LSP library, state is decorated with a few attributes which are
modified by some productions. Some of them productions are only to be used by
the library but some are useful to developers adding
LSP information to there language. The `State.sv` file contains the collection
of productions that are used to modify the State. The two important ones for
LSP developers are
1. ```abstract production stateNewServerInitiatedMessages``` with signature

```top::State ::= newMessages::[ServerInitiatedMessages] oldState::State``` which
adds a list of messages that the server should send to the client. Server
initiated messages are used for sending errors and showing or logging messages
on the client side.

2. ```abstract production setInitializeSettings ``` with signature

```top::State ::= params::InitializeParams oldState::State``` which is used if
the server you are ever writing has any need for the parameters used to
initialize the server, they should be saved to the state by modifying the state
with this production.

The state is meant to be added to by developers adding LSP support to there
language as we will see later.

#### Hooking Up To the LSP Library
This section describes how the file `Hookup.sv` in the `oberon0:lsp` grammar in
the Oberon0LSP repository was initially written.

As described above, to hook into the LSP library we need to aspect the function
and add are request and notification handlers, which we have none of right now.
Make sure to import lib:lsp so you know about this function.
```
import lib:lsp;
aspect function buildInterface
LSP_Interface ::=
{
  requestHandlers <- [];
  notificationHandlers <- [];
}
```

#### Deciding How Our State Should Look
This will likely change based on your language and its needs, but a useful
suggestion is to keep the AST of all of the necessary files in the state,
the text of the file for all open files and a boolean indicating whether the
AST is accurate based on the text or whether a new AST is needed.
Pretty much everything we do will need to utilize the abstract syntax tree.

Thus, we will create a nonterminal

`LSPDocument with docName, docText, rootAst, lastValidAst;`
where the 4 attributes have types
1. `synthesized attribute docName :: String;`
2. `synthesized attribute docText :: String;`
3. `synthesized attribute rootAst :: Maybe<Decorated Module>;`
4. `synthesized attribute lastValidAst :: Maybe<Decorated Module>;`

which represent the name, text, AST of the root, and the last valid AST.
The ASTs are wrapped in `Maybe` since it is possible that the
code in the text editor at any given point may not be parsable and we may not
be able to form an abstract syntax tree. However, for some requests using the
last available reference will provide good enough results for example when
doing completion. Thus, we keep around the AST that matches the current
document text (if one exists) and the last valid AST we have for this document.

We then use 2 productions to create a new document and modify the text of the
document. The new AST is stored as an attribute, but since Silver is lazy this
AST will not be determined until it is requested during a later request.
These productions have names and signatures
1. `lspDocument`: `top::LSPDocument ::= name::String text::String`
3. `lspDocumentNewText`: `top::LSPDocument ::= newText::String doc::LSPDocument`

in the `State.sv` file and have equations which simply assign the trees passed
in to the attributes and set the astDirty flag as appropriate.

Now we will add these documents to our `State` with the attribute

`synthesized attribute documents :: rtm:Map<String LSPDocument> occurs on State`

Note this attribute utilizes the binary tree map provided by Silver in the
grammar `silver:util:raw:treemap` which we have imported as `rtm`. This map
utilizes the document names as the keys and the documents themselves as the
value.

To add or update documents to the state we create the abstract production where
we check if the document is already in the map and if it is not we add otherwise
we update it. Note how we forward to oldState to fill in values for all other
attribute equations that we are not directly applicable to this production. This
is a pattern that appears in every state production except the `initialState`
production which we will need to aspect to provide an initial value for our
production.
```
abstract production updateDocumentInState
top::State ::= doc::LSPDocument oldState::State
{
  top.documents =
    if null(rtm:lookup(doc.docname, oldState.documents))
    then rtm:add([pair(doc.docName, doc)], oldState.documents)
    else rtm:update(doc.docName, [doc], oldState.documents);
  forwards to oldState;
}
aspect production initialState
top::State ::=
{
  top.documents = rtm:empty(compareString);
}
```
To provide easy access to documents in the state we write a helper function
`getDocument` with signature
`Maybe<LSPDocument> ::= docName::String state::State`
which lookups up the
document in the map and returns it if it exists.

After adding this document map to our State, all that we need to do now is add
write the handlers to handle the requests and notifications of the LSP function
and update the state as we need. To write these handlers, we will often add
attributes to the root and aspect the necessary productions to calculate these
attributes properly.

#### Handling the Initialize Request
The first request we need to worry about is the initialize request which is the
first request the client sends to the server. The client needs to receive a
response from this request before it sends any more requests. Let us write this
function now.

The `RequestHandler` that is run for the initialize request is formed with the
`initializeRequestHandler` production which has the signature.

`top::RequestHandler ::= func::LSPRequestFunction<InitializeRequest InitializeResult>`

Thus, as described above, we need to write a function with the signature below.
```
function handleInitializeRequest
Pair<State InitializeResult> ::= state::State input::InitializeRequest io::IO
```
This function serves two purposes
1. To set the initialize parameters in the `State` if they are needed later.
2. To return the capabilities of your server. There are many capabilities a
server can have which are all optional (meaning they are wrapped in a
`Maybe`). They are provided in this order
  - `Maybe<TextDocumentSyncOptions>`: which states how the server wants to
  handle making sure the documents are synced between the client and the
   server.
  - `Maybe<Boolean>`: which states whether or not the client supports hover
   requests
  - `Maybe<CompletionOptions>`: which if `isJust` indicates the server
   supports completion requests with the specified options
  - `Maybe<SignatureHelpOptions>`: which if `isJust` indicates the server
  supports signature help requests with the specified options
  - `Maybe<Boolean>`: which states whether or not the server supports go to
  definition requests.
  - `Maybe<Boolean>`: which states whether or not the server supports go to
  type definition requests.
  - `Maybe<Boolean>`: which states whether or not the server supports go to
  implementation requests.
  - `Maybe<Boolean>`: which states whether or not the server supports find
  references requests.
  - `Maybe<Boolean>`: which states whether or not the server supports
  document highlight requests.
  - `Maybe<Boolean>`: which states whether or not the server support
  workspace symbol requests.
  - `Maybe<Boolean>`: which states whether or not the server supports code
  action requests.
  - `Maybe<CodeLensOptions>`: which if `isJust` indicates the server supports
  code lens requests with the specified options.
  - `Maybe<Boolean>` which states whether or not the server supports document
  formatting requests.
  - `Maybe<Boolean>` which states whether or not the server supports range
  formatting requests.
  - `Maybe<DocumentOnTypeFormattingOptions>` which states whether or not the
  server supports on type formatting requests.
  - `Maybe<Boolean>`: which states whether or not the server supports rename
  requests.
  - `Maybe<DocumentLinkOptions>`: which if `isJust` indicates the server
  supports document link requests with the specified options.
  - `Maybe<Boolean>`: which states whether or not the server supports folding
  range requests.
  - `Maybe<Boolean>`: which states whether or not the server supports go to
  declaration requests.
  - `Maybe<ExecuteCommandOptions`: which if `isJust` indicates the server
  supports execute command requests with the specified options.
  - `Maybe<ServerWorkspaceCapabilities>`: which are used to indicate whether
  or not the server supports workspace folder requests.

  The protocol is large and a language developer is unlikely to fully
  support every request in the protocol. Thus, a blank template is provided
  below for the initialize request function that you can fill in with the
  specific details of your server.

```
function handleInitializeRequest
Pair<State InitializeResult> ::= state::State input::InitializeRequest io::IO
{
      local serverCapabilitiesVal :: ServerCapabilities =
        serverCapabilities(
          nothing(), -- sync capabilities: Maybe<TextDocumentSyncOptions>
          nothing(), -- hover capabilities: Maybe<Boolean>
          nothing(), -- completion capabilities: Maybe<CompletionOptions>
          nothing(), -- signature help capabilities: Maybe<SignatureHelpOptions>
          nothing(), -- goto definition support: Maybe<Boolean>
          nothing(), -- goto type definition support: Maybe<Boolean>
          nothing(), -- goto implementation support: Maybe<Boolean>
          nothing(), -- find references support: Maybe<Boolean>
          nothing(), -- document highlight support: Maybe<Boolean>
          nothing(), -- workspace symbol support: Maybe<Boolean>
          nothing(), -- code action support: Maybe<Boolean>
          nothing(), -- code lens support: Maybe<CodeLensOptions>
          nothing(), -- document formatting support: Maybe<Boolean>
          nothing(), -- document range formatting support: Maybe<Boolean>
          nothing(), -- document on type formatting support
          nothing(), -- rename support: Maybe<Boolean>
          nothing(), -- document link support: Maybe<DocumentLinkOptions>
          nothing(), -- folding range support: Maybe<Boolean>
          nothing(), -- goto declaration support: Maybe<Boolean>
          nothing(), -- execute command support: Maybe<ExecuteCommandOptions>
          nothing()); -- workspace capabilities: Maybe<ServerWorkspaceCapabilities>

     -- add the initialize params if they are needed later
      local newState :: State =
          setInitializeSettings(input.initializeRequestParams, state);
      return pair(newState, initializeResult(serverCapabilitiesVal));
}
```

From here, you will likely need to change the server capabilities every time
you add a new feature to your language server.

The last step to add this function to the LSP is to make sure it appears in the
interface by wrapping it in a `RequestHandler` and adding it to the
`buildInterface` production.

We do this as so
```
aspect function buildInterface
LSP_Interface ::=
{
  requestHandlers <- [initializeRequestHandler(handleInitializeRequest)];
  notificationHandlers <- [];
}
```

#### Handling Document Synchronization
To handle document synchronization we need to handle 5 notifications and 1
 request
1. [Did Open Notification](https://microsoft.github.io/language-server-protocol/specification#textDocument_didOpen)
2. [Did Change Notification](https://microsoft.github.io/language-server-protocol/specification#textDocument_didChange)
3. [Will Save Notification](https://microsoft.github.io/language-server-protocol/specification#textDocument_willSave)
4. [Will Save Wait Until Request](https://microsoft.github.io/language-server-protocol/specification#textDocument_willSaveWaitUntil)
5. [Did Save Notification](https://microsoft.github.io/language-server-protocol/specification#textDocument_didSave)
6. [Did Close Notification](https://microsoft.github.io/language-server-protocol/specification#textDocument_didClose)

Not all of these need to be supported by the server and the server will specify
which ones you want in the `TextDocumentSyncOptions` you send to a server.
The TextDocumentSyncOptions are created with this structure
```
textDocumentSyncOptions(
  nothing(), - does the server want to receive open and close notifications: Maybe<Boolean>
  nothing(), - how the server wants to receive change notifications: Maybe<TextDocumentSyncKind>
  nothing(), - does the server want to receive will save notifications: Maybe<Boolean>
  nothing(), - does the server want to recieve will save wait until notifications: Maybe<Boolean>
  nothing(), - how the server wants to receive did save notifications: Maybe<SaveOptions>
  );
```
For Oberon0 we will choose to receive all of the notifications and requests.
Thus, we need to decide which `TextDocumentSyncKind` to use and what
`SaveOptions` we want.

There are 3 types of `TextDocumentSyncKind`
  1. __None__ created using the `textDocumentSyncKindNone()` production.
  This will cause the client to not send Did Change Notifications.
  2. __Full__ created using the `textDocumentSyncKindFull()` production.
  This will cause the client to send the entire document contents on every
   change
  3. __Incremental__ created using the `textDocumentSyncKindIncremental()`
  production. This will cause the client to send changes incrementally to the
  server.

For the simplicity of the Oberon0 implementation, we will choose to get the
full contents of the document on every change so we do not need to worry
about incrementally updating our documents.

There is one flag that needs to be set for `SaveOptions`. It is whether or not
to include the text on save. Although the document should be up to date based
on the latest Did Change Notification we will choose to include the text anyway
just in case.

Thus, for Oberon0 our `TextDocumentSyncOptions` will look like
```
textDocumentSyncOptions(
  just(true),
  just(textDocumentSyncKindFull()),
  just(true),
  just(true),
  just(saveOptions(just(true))))
```
We need to change the first `nothing()` in our server capabilities to a `just`
of the code above.

Now let's handle tbe requests!

##### Handling Did Open Notification

When the client sends a `DidOpenTextDocumentNotification` it sends the
`DidOpenTextDocumentParams` which has a `TextDocumentItem` which contains the
document URI, the language identifier, the version number and the text of the
document. Thus, since our server is only handling one language and is
explicitly doing version management, we only cares about document name and
the contents of the document. Thus, we will need to grab the document and text
off the parameters and add it to our `State`.

__Note__: A `DocumentUri` is a `String` but is guaranteed to be a well formed
URI. Thus, if the document `/my/stuff` is opened, the server will receive that
the uri `file:///my/stuff` was opened. The LSP library allows for easy
conversion between the two with the functions `fileToUri` and `uriToFile`.

Thus, let us add the file and the text to our document maps if they
 are not already there otherwise update the text in the map.

```
function handleDidOpenNotification
State ::= state::State input::DidOpenTextDocumentNotification io::IO
{
  -- get the pertinent information from the parameters
  local fileOpened::String = uriToFile(input.didOpenTextDocumentParams.openedDocument.uri);
  local fileText::String = input.didOpenTextDocumentParams.openedDocument.documentText;

  local doc :: LSPDocument = updateOrCreateDocument(fileOpened, fileText, state);

  -- update or add to the state
  return updateDocumentInState(doc, state);
}
```
Notice we wrote a helper function that is very useful for working
with text synchronization called `updateOrCreateDocument` which can
be found in `State.sv` and takes in the file name, file text and
state and returns the updated or created LSPDocument for that file
with the specified text.

##### Handling Did Change Notification
When the client sends a `DidChangeTextDocumentNotification` it sends
the `DidChangeTextDocumentParams` which has a
`VersionedTextDocumentIdentifier` and a
 `[TextDocumentContentChangeEvent]`. If you are making changes
 incrementally, if the content changes are `c1` and `c2` then if the
 document starts in state `S`, change `c1` moves `S` to `S'` and
change `c2` moves `S'` to `S''`.

A `TextDocumentContentChangeEvent` has 3 attributes
1. `changedRange :: Maybe<Range>` which has the range of the document
 where the text was changed
2. `changedRangeLength :: Maybe<Integer>`
the length of the changed range
3. `newText :: String` the new text that is to go in that range.

_Note_: If the changed range is `nothing` then the `newText` is
the full contents of the document.

Thus, since for simplicity we are having the client send the
entire document on every change, we need not worry about making
incremental changes to our document.

Thus, let us update the text in our document map on this request.

```
function handleDidChangeNotification
State ::= state::State input::DidChangeTextDocumentNotification io::IO
{
  -- get the pertinent information from the parameters
  local fileChanged::String = uriToFile(input.didChangeTextDocumentParams.versionedTextDocumentId.uri);
  local changes::[TextDocumentContentChangeEvent] = input.didChangeTextDocumentParams.contentChanges;
  -- assume only 1 change because all text is sent on every change
  local newText::String = head(changes).newText;

  -- update or create the document
  local newDoc :: LSPDocument = updateOrCreateDocument(fileChanged, newText, state);
  -- don't change the state if no changes were sent
  return
    if null(changes)
    then state
    else updateDocumentInState(newDoc, state);
}
```
##### Handling Will Save Notification
The `WillSaveTextDocumentNotification` is sent from the client to the
server when
the client is about to save, but before it actually has saved.
This can be a place where errors are sent back to the client or
other interesting computation is done. At this point in the tutorial
we do not have any interesting computation to do so this function
will simply return the state.

A `WillSaveTextDocumentNotification` contains an attribute
`willSaveTextDocumentParams :: WillSaveTextDocumentParams`
which has attributes `documentId :: TextDocumentIdentifier` and
`reasonForSave :: TextDocumentSaveReason`.
```
function handleWillSaveNotification
State ::= state::State input::WillSaveTextDocumentNotification io::IO
{
  return state;
}
```

##### Handling Will Save Wait Until Request
The `WillSaveWaitUntilRequest` is sent from the client to the server
at the same time a `WillSaveNotification` is sent. The server is
allowed to send back a `[TextEdit]` representing a collection of edits
to be made before a save occurs. The parameters sent are the same as
for a `WillSaveTextDocumentNotification`. This is a place where
formatting could be done if that is desired by your language. We will
do this later in this example once we learn more about formatting.
For now we will return a null response instead.
```
function handleWillSaveWaitUntilRequest
Pair<State WillSaveWaitUntilResult> ::= state::State input::WillSaveWaitUntilRequest io::IO
{
  return pair(state, nullWillSaveWaitUntilResult());
}
```
__Note__: This is an important place to mention a naming convention
in the LSP library.
It is best explained by example.
For the `WillSaveWaitUntilResult` we have two
abstract productions to create them: `nullWillSaveWaitUntilResult`
and `willSaveWaitUntilResultTextEditList`. The default is the name
of the result (i.e. `willSaveWaitUntilRequest`) followed by the type
of the response (i.e. `TextEditList`) or if `null` can be returned
then `null` followed by the name of the result
`WillSaveWaitUntilResult`.

##### Handling Did Save Notification
The `DidSaveTextDocumentNotification` is sent to the server
from the client after the client has saved the document to disk.

The `DidSaveTextDocumentNotification` has
`didSaveTextDocumentParams :: DidSaveTextDocumentParams`

The `didSaveTextDocumentParams` has attributes
`documentId :: TextDocumentIdentifier` and
`contentWhenSaved :: Maybe<String>`.

The `contentWhenSaved` is a `Maybe` because the client only sends the
contents of the document if the server explicitly asks for them in
the `InitializeResult`. We did so we will update the document in our
state so it matches what is on disk.

```
function handleDidSaveNotification
State ::= state::State input::DidSaveTextDocumentNotification io::IO
{
  local fileSaved::String = input.didSaveTextDocumentParams.documentId.uri;
  -- we know this is fromJust from what we sent in our initial response
  local fileText::String = input.didSaveTextDocumentParams.contentWhenSaved.fromJust;

  -- update or create the document
  local newDoc :: LSPDocument = updateOrCreateDocument(fileSaved, fileText, state);
  return updateDocumentInState(newDoc, state);
}
```
The Did Save Notification is also a good place to compute and return errors
which we will discuss later in this example.

##### Handling Did Close Notification
Here we could remove the `LSPDocument` from our `State`, but we our
going to prefer to avoid recomputation in the case the file is
reopened and use a little bit more of the users RAM.

Thus, this will be another function that does nothing to the `State`.
For reference, the parameters given on the close notification is
simply the `TextDocumentIdentifier` of the document closed.

```
function handleDidCloseNotification
State ::= state::State input::DidCloseTextDocumentNotification io::IO
{
  return state;
}
```

##### Hooking in Our Text Document Synchronization Handlers
Don't forget for these functions to actually be run when the
appropriate method is sent from the client to the server, we need
to add these to the `requestHandlers` and `notificationHandlers` in
our aspect function `buildInterface`. At this point these functions
should look like
```
requestHandlers <- [
    initializeRequestHandler(handleInitializeRequest),             
    willSaveWaitUntilRequestHandler(handleWillSaveWaitUntilRequest)
];
notificationHandlers <- [
    didOpenNotificationHandler(handleDidOpenNotification),           
    didChangeNotificationHandler(handleDidChangeNotification),
    willSaveNotificationHandler(handleWillSaveNotification),
    didSaveNotificationHandler(handleDidSaveNotification),
    didCloseNotificationHandler(handleDidCloseNotification)
  ];
```
#### Error Messages in Silver and the Language Server Protocol
The language server protocol states that the server controls
the error messages. This means that no request is sent from
the client to the server to receive error messages. Instead,
the server sends a message to the client with error messages.

Error messages are sent over the language server protocol
on a per file basis. That is all the errors for a single
file are sent in one server initiated message.

Thus, we need to be able to do two things.
1. _Send server initiated messages_ - This is done by adding
the messages to the `State` using the production
```
abstract production stateNewServerInitiatedMessages                             
  top::State ::= newMessages::[ServerInitiatedMessage] oldState::State
```
These messages are removed from the `State` in the language
agnostic server and sent to the client.
2. Convert Silver's `Message` type to this
 `ServerInitiatedMessage` message type that follows the
 `PublishDiagnosticNotification` type defined in the
 language server protocol. The LSP library defines a very
 useful attribute and function called
  ```
  synthesized attribute equivalentDiagnostic::SilverDiagnostic occurs on Message, ParseError
function silverDiagnosticsToServerInitiatedMessages
[ServerInitiatedMessage] ::= d::[SilverDiagnostic]
```
The `equivalentDiagnostic` corresponds to a diagnostic
message that bridges the gap between the way Silver handles
error messages and by the way the Language Server Protocol
handles error messages. This diagnostic can be grabbed off
syntax errors on the `ParseError` nonterminal or on
semantic errors on the `Message` nonterminal.

Then, to convert these diagnostics to the form needed to
send them to a client via the language server protocol
calling the function
 `silverDiagnosticsToServerInitiatedMessages` will do all
the necessary conversions.

#### Sending Errors to the Client in Obeorn0

Thus, to do this in Oberon0 we must decide when to send the
error messages. A simple solution is to do this when a file
is saved. Then, we will try to parse the file and form the
abstract syntax tree and report any errors encountered along
the way.

Thus, we will need to build on our
 `handleDidSaveNotification` that we started earlier.

Thus, we need to attempt to parse the file to see if there
is a parse error we need to report. If there is no parse
error than we can grab the AST of the `LSPDocument` and the
errors off of that. Then, we need to turn the errors into
error messages which we do with the
 `silverDiagnosticsToServerInitiatedMessages` function.
 Lastly, we need to add these new messages to the `State`
 which we do with the production
  `stateNewServerInitiatedMessages`.
```
function handleDidSaveNotification
State ::= state::State input::DidSaveTextDocumentNotification io::IO
{
  local fileSaved::String = input.didSaveTextDocumentParams.documentId.uri;
  -- we know this is fromJust from what we sent in our initial response
  local fileText::String = input.didSaveTextDocumentParams.contentWhenSaved.fromJust;
  -- update or create the document
  local newDoc :: LSPDocument =
    updateOrCreateDocument(fileSaved, fileText, state);

  -- NEW STUFF
  -- attempt to parse
  local parseAttempt :: ParseResult<Module_c> = parse(fileText, fileSaved);

  -- get the AST errors if parse succeeded from the updated document or parse error otherwise
  local errorsToSend :: [SilverDiagnostic] =
    if parseAttempt.parseSuccess
    then map((.equivalentDiagnostic), newDoc.rootAst.fromJust.errors)
    else [parseAttempt.parseError.equivalentDiagnostic];

    -- turn the errors into error messages
    local errorMessages :: [ServerInitiatedMessage] =
      silverDiagnosticsToServerInitiatedMessages(errorsToSend);

  -- add the new messages to the state as well
  return stateNewServerInitiatedMessages(errorMessages, updateDocumentInState(newDoc, state));
}
```
Thus, error messages are now reported after files are saved
to disk.

#### Finding References and Going to Definitions/Declarations
To go to the definition or declaration of any variable or find all the
references of a given variable, you need to keep track of all the references
to all the variables and the location of each reference along with the
location of the corresponding declaration. Thus, we create a nonterminal
`Reference` with attributes `name`, `referenceLocation` and
 `definitionLocation`.

 We then collect every time a variable that
 references something else is used and pass that up the abstract syntax
tree. In Oberon0 we call this attribute `referenceContribs :: [Reference]`.
 _Note_: A list is used to collect the references, but if more efficient
 lookup is required this list can be turned into whatever conveinient
 data structure is necessary. However, note that the Language Server
 Protocol will be giving you a position when these requests are made. Thus,
 the data structure should be able to lookup whether the position
 is contained within the location of the reference.

 For the simplicity of this example we will simply look through the
 `referenceContribs` list. To pass these up the AST we aspect almost every
 production in Oberon0 which can be seen in the `ReferenceTracker.sv` file.
 The only interesting cases happen in the `Name` and `TypeName` production
 otherwises we simply collect the reference contributions of the children
 and append them together.

##### Implementing Go To Definition
Now that we have a list of all of the references in the Oberon0 file we are
dealing with let's look at how the Language Server Protocol handles go to
definition requests and responses. The `GoToDefintionRequest` has an
attribute `gotoDefinitionRequestParams :: TextDocumentPositionParams` which
has attributes `position :: Position` which contain the position and
`documentId :: TextDocumentIdentifier`. The `TextDocumentIdentifier`
 nonterminal has an attribute `uri`.

Thus, to access the DocumentUri and Position of a `GoToDefintionRequest`
the access would be `_.gotoDefinitionRequestParams.position` and
`_.gotoDefinitionRequestParams.documentId.uri`.

After grabbing the document with this file name, we look through our
references list to find the reference that contains the position specified
in the parameters. After finding this reference, we can simply grab and
return the attribute providing the definition location.

This can be abstracted to a conveinient reusable function
```
function getDefinitionLocation
Maybe<Location> ::= params::TextDocumentPositionParams state::State
{
  local file :: String = uriToFile(params.textDocumentId.uri);
  local docM :: Maybe<LSPDocument> = getDocument(file, state);

  local ast :: Maybe<Decorated Module> = docMaybe.fromJust.lastValidAst;
  local refM :: Maybe<Reference> = find(referenceContainsPosition(params.position, _), ast.fromJust.referenceContribs);

  return
    if docM.isJust && ast.isJust && refM.isJust
    then just(refM.fromJust.definitionLocation)
    else nothing();
}
```
This function takes in the parameters of the goto requests and the state
and returns a location for the definition if it exists. Now we need to
fit the appropriate signature for the go to definition request and provide
the appropriate return value by the Language Server Protocol.

That can be done as follows
```
function handleGoToDef
Pair<State GoToDefinitionResult> ::= state::State input::GoToDefinitionRequest io::IO
{
  local defLoc :: Maybe<Location> = getDefinitionLocation(input.gotoDefinitionRequestParams, state);
  return
  if defLoc.isJust
  then pair(state, gotoDefinitionResultLocation(silverLocationToLSPLocation(defLoc.fromJust)))
  else pair(state, nullGoToDefinitionResult());
}
```
Recall the naming conventions established earlier for result types. Look
back earlier in the document if you have forgotten as this will make the
LSP library much more intuitive.

__Note__: There are 3 other very similar requests
`GoToDeclarationRequest`, `GoToImplementationRequest` and
`GoToTypeDefinitionRequest`. All 3 of these requests all have input
parameters of type `TextDocumentPositionParams` and return a `Location`.
These would be implemented in an almost identical fashion.

##### Implementing Find References Request
To find all the references for a particular reference, we need to find the
location of the definition of the reference and then find all other
references with the identical location. Thus, our handy
 `getDefinitionLocation` will be of great use.

 For a FindReferencesRequest, the parameters are `ReferenceParams` which
 contain the same parameters as `TextDocumentPositionParams`, but also
 contain an additional attribute of type `ReferenceContext` which as of
 now contains a boolean indicating whether to include the declaration in
 the references. For simplicity in this tutorial, we will assume this is
 always true. The `FindReferencesResult` takes only a list of locations.

Thus, to convert `ReferenceParams` to `TextDocumentPositionParams` we will
use a simple conversion function that exists within the LSP library called
`referenceParamsToTextDocumentPositionParams`
 ```
 function handleFindReferences
 Pair<State FindReferencesResult> ::= state::State input::FindReferencesRequest io::IO
 {
    local file :: String = uriToFile(input.findReferenceParams.textDocumentId.uri);
    local docM :: Maybe<LSPDocument> = getDocument(file, state);

    local ast :: Maybe<Decorated Module> = docMaybe.fromJust.lastValidAst;

    local defLoc :: Maybe<Location> = getDefinitionLocation(referenceParamsToTextDocumentPositionParams(input.findReferenceParams), state);

    local matchingRefs :: [Reference] = filter(referenceHasDefinitionLocation(defLoc.fromJust, _), ast.referenceContribs)

    return
    if defLoc.isJust
    then
    else pair(state, nullFindReferencesResult());

 }
```
