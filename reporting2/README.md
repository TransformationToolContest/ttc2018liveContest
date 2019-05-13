# Visualization

Install R. For Ubuntu 16.04+, the following works:

```console
sudo apt install r-base r-base-dev
```

Run R and install the [Tidyverse](https://www.tidyverse.org/) package:

```R
install.packages("tidyverse")
```

This [might fail](https://github.com/FTSRG/cheat-sheets/wiki/R-programming-language#installing-tidyverse-on-ubuntu) to the lack of some packages (e.g. `rvest`, `xml2`), which can be installed with

```console
sudo apt install -y libssl-dev libxml2-dev libcurl4-openssl-dev
```

To generate the plot, use the Makefile:

```console
make
```
