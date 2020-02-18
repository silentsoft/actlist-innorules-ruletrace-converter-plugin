# Innorules Ruletrace Converter

![](https://img.shields.io/badge/release-v1.2.0-blue.svg)
[![license](https://img.shields.io/badge/license-Apache--2.0-green.svg)](https://github.com/silentsoft/actlist-innorules-ruletrace-converter-plugin/blob/master/LICENSE.txt)
[![HitCount](http://hits.dwyl.io/silentsoft/actlist-innorules-ruletrace-converter-plugin.svg)](http://hits.dwyl.io/silentsoft/actlist-innorules-ruletrace-converter-plugin)

> Drag and drop your rule trace files to convert special delimiters.

## Features
  - Convert special delimiters on rule trace `.xml`.
    - replace `<>` to ""
    - replace `<CR><LF>` to `:`
    - replace `STX` and `ETX` to `:`
  - Insert XML specification to first line if not exists.
  - Automatic conversion to specific directory every 5 seconds.
  - Automatic encoding detaction (`EUC-KR` or `UTF-8`)

## Packaging
```
$ mvn clean package
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please note we have a [CODE_OF_CONDUCT](https://github.com/silentsoft/actlist-innorules-ruletrace-converter-plugin/blob/master/CODE_OF_CONDUCT.md), please follow it in all your interactions with the project.

## License
Please refer to [LICENSE](https://github.com/silentsoft/actlist-innorules-ruletrace-converter-plugin/blob/master/LICENSE.txt) and [NOTICE](https://github.com/silentsoft/actlist-innorules-ruletrace-converter-plugin/blob/master/NOTICE.md).
