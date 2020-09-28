## Accounting data management: use case invariant considerations

In a modern IT system, all business entities are striving for account-based data management. This will ensure that all business transactions in the history can be traced meticulously, and we also have the flexibility to carry out business transactions in the past and future at any time (keyword: retroactive changes). In a modern development environment, there is also the chance to implement the relevant considerations once and to reuse them for any useful application without additional effort.

Generically, one can consider that every business entity is divided into three parts:

<body><div class="mxgraph" style="max-width:100%;border:1px solid transparent;" data-mxgraph="{&quot;highlight&quot;:&quot;#0000ff&quot;,&quot;nav&quot;:true,&quot;resize&quot;:true,&quot;toolbar&quot;:&quot;zoom layers lightbox&quot;,&quot;edit&quot;:&quot;_blank&quot;,&quot;xml&quot;:&quot;&lt;mxfile host=\&quot;app.diagrams.net\&quot; modified=\&quot;2020-09-28T11:27:00.778Z\&quot; agent=\&quot;5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36\&quot; etag=\&quot;iOsUbiDtn8tnNLKZJloY\&quot; version=\&quot;13.7.2\&quot; type=\&quot;github\&quot;&gt;&lt;diagram id=\&quot;gOKZhKgsurOC0WEhZ5pP\&quot; name=\&quot;Data\&quot;&gt;7ZrbctowEIafhstmfDZchkNTpmmnLWk6zZ1iy1itsFxZBOjTd20kTjIJJNgwHQMzSL9ka72fVl4LWnZvMr/hKI0/sRDTlmWE85bdb1mWaXdM+MqVhVSMtrFUxpyEUlsLI/IXq45SnZIQZ1sdBWNUkHRbDFiS4EBsaYhzNtvuFjG6PWqKxlgTRgGiuvqDhCJeqm3LX+sfMBnHamTT6yxbJkh1lleSxShksw3JHrTsHmdMLEuTeQ/T3HvKL8vj3u9pXRnGcSIOOeDX/Zc/P78+BMEs8+ntw/ds+vnrO1ca94ToVF6xtFYslAs4myYhzs9itOzuLCYCj1IU5K0zoA5aLCYUaiYUdaukoU+YCzzfkKSVN5hNsOAL6CJbPWmTnDKucuBs7X/TcKQYbzjf6kgRSejj1bnXfoGCdM0xbjJL3ORRGLcbkqd8RErGSdHg/ZnmRLsUR2Jdg9JYfhdHPSrh/vp22B/e/VQNYN7jbmfQilGUusMH3Cq2IWSCs9+4xyjjoCQsgZ7diFC6IymrC1vtbo6IwNS/lvKEhGE+SCnz7VlxAuydHeyeq2EvhV4Zc7sq5t3vo+HnwWgE5/g4aMjvkvfcsoCvFb1TA/r+9d11w36Hve+cnb378h0RbuVpXhTosfDUBoQyp2UCcSFTm9xtkKsIRBLM5TEBoxSlGSlOtuwRExreogWbCjWMqu3ntYm6ZdmG4Ro40uZF0WL2u738CDBkQ4+K12m4rgCp5dz1r/QFHWzR0dpuVWi9w9HCxQuC6DfIKVEyPoSyTjHkLL1DfIyFFFJGEoH54An8mkmtJEAFS2XjMkSL4iMTgk1khUtvrU5auMrtwgcc2jPA1S5cTQ/q5roOn7w7B+YJTAqYgvk5MMrEDGeilPvzIfLybDgfa19jfT/snxR38cCB1rj3xd9xOBl4NaLFg0IMkY2T5yOYkvRejQ/lD9Li/YvEwSH/fAgdhd736yTf1sgPoogEBCfBos4JELn5u5oJ4BUvnefyVQnP+TbLTbxenXg7Ot45yQTQxQ3dt9I11VPPuaLX0tfty0q/TpAbOe7OA09J0lt6t3SqSnotfc1sMqNXZEar2fv2zKg61voC+vF/zIzeluisIuLNiU5lIG197/bq6qq5B74a5HEZTnVc9c3mhusJuB6a21QG1rEuPLe5qB0kr72zNWjrWZLddurkV/aLQJMlHZ0lreLgqHtrzaz1nwD6TZa0NyIuN0ty9P38mtNdz0Ad068GpNX3PcOoFeQzWVKtXPXN/Jo3eP9Prqb68fNsYPUdoCb9PQFYSz1XnOu5xtE3mRqwJwBrG5UtxVBd/0GtaNv4n589+Ac=&lt;/diagram&gt;&lt;/mxfile&gt;&quot;}"></div>
<script type="text/javascript" src="https://app.diagrams.net/js/viewer-static.min.js"></script>
(blue: technical key, green: technical foreign key, grey: dependent data)

The fields VID, KID, DID are technical key fields in the form of a UUID (globally unique, without order relation) or in form of a big Integer. The (first) letter V stands for **validity**, K for **key** and D for **data**. As indicated in the diagram, there are three tables in the relational database model.

#### Validity

It is immediately apparent that the validity is completely independent of the use case and therefore only needs to occur once for all business entities in the respective technical service context.

* The **efficiency** is a time specification which, according to its name, makes a statement as to whether dependent data within a certain factual context must be taken into account. It is an "included start" ( = "effective from included") in the form of a date, time or time stamp. At the moment it is not yet clear to me what granularity is required here.
* The **existence** is a time specification in the form of a time stamp that is as fine-grained as possible. It makes the statement from when dependent data have existed. In other words, it is the point in time when an employee, a customer, a program created this data record.

#### Business Key

The business key is the factually defining dimension or set of such dimensions that defines the business data in more detail. Usually, in our context, a field such as policy holder number, contract, etc. will appear here. The business key has no immediate validity. A time reference can be indirectly established via the business data assigned to it. This makes sense because a business key without business data has no right to exist.

#### Business Data

The business data is dependent data that must be assigned by force to a business key characteristic and can be aggregated in some form (such as amounts). In addition to the reference to a transaction key, they also have a reference to a validity.

#### Assumptions

* Only insertions are permitted. Changes and deletions are prohibited.
* Since the validity fields only represent a beginning in each case, all business changes up to complete deletion must be realised via aggregates.
* Aggegrate types depend on the business purpose of a field, see examples.

[pic]

* Where do business foreign keys belong? It depends:
    * A foreign key with reference to a "business change" belongs in the business data.
    * A foreign key with reference to all data records with the same key belongs in the business key. In this case it has the "content severity" of the business key.
	
#### Implementation

The whole thing can be developed with Java Generics in the form of a linkable utility library. In this way, the application developer can be provided with complete CRUD functionality without having to learn the schema of data storage. Of course, there must be an understanding of the terms of validity - but otherwise the application developer can speak of "change sentence" or "delete sentence".