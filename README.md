Auhor: Svyat Podmogayev(s.podmogayev@gmail.com) with the support of Dmitry Kurinsky.

The plugin provides the ability to use memcached utility. All plugin's congigurations concluded in block with name `memcached`. For connection to memcached server you must to indicate *host* with string value and *port* with integer value in sub-block `settings`. Caches configurations indicates as list of blocks, which name is cache's name, that enclosed in `caches`-block. In each cache-block you must indicate `timeToLive` param, that images a time after end which an object will be deleted from cache.

The plugin depends of: *cache:1.0.1* plugin
