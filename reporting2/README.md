# Visualization

Install R and pandoc.

## Ubuntu 

For Ubuntu 16.04+, the following works:

Get the latest version as described in: https://cran.r-project.org/bin/linux/ubuntu/README.html

```console
sudo apt install -y r-base r-base-dev pandoc libssl-dev libxml2-dev libcurl4-openssl-dev
```

## Fedora

On Fedora:

```console
sudo dnf install -y R R-Rcpp-devel rstudio-desktop openssl-devel libxml2-devel libcurl-devel
```

## R packages

Run R and install the [Tidyverse](https://www.tidyverse.org/) and [TinyTeX](https://yihui.name/tinytex/) package.

To run GCC/G++ on multiple (e.g. `8`) threads, set the following flags:

```bash
mkdir ~/.R
echo "MAKEFLAGS = -j8" > ~/.R/Makevars
```

Install the packages:

```R
install.packages(c("tidyverse", "ggpubr", "tinytex"))
tinytex::install_tinytex()
```

(This [might fail](https://github.com/FTSRG/cheat-sheets/wiki/R-programming-language#installing-tidyverse-on-ubuntu) if you haven't installed the second group of packages, `rvest`, `xml2`, .etc.)

To generate the plot, use the Makefile:

```console
make
```

## Concatenating results

To produce a single file `output.csv` from the results, use the following command: (cf. `paper-repo/output/README.md`)

```bash
cat output/header.csv <(tail -qn +2 output/output-docker-*.csv) > output.csv
```
