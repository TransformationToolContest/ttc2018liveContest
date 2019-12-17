# Visualization

Install R and pandoc. For Ubuntu 16.04+, the following works:

```console
# R base
sudo apt install -y r-base r-base-dev pandoc
# for tidyverse
sudo apt install -y libssl-dev libxml2-dev libcurl4-openssl-dev

# both
sudo apt install -y r-base r-base-dev pandoc libssl-dev libxml2-dev libcurl4-openssl-dev
```

Run R and install the [Tidyverse](https://www.tidyverse.org/) and [TinyTeX](https://yihui.name/tinytex/) package.

To run GCC/G++ on multiple threads, set the following flags:

```bash
mkdir ~/.R
echo "MAKEFLAGS = -j40" > ~/.R/Makevars
```

Install the packages:

```R
install.packages("tidyverse")

install.packages("tinytex")
tinytex::install_tinytex()
```

(This [might fail](https://github.com/FTSRG/cheat-sheets/wiki/R-programming-language#installing-tidyverse-on-ubuntu) if you haven't installed the second group of packages, `rvest`, `xml2`, .etc.)

To generate the plot, use the Makefile:

```console
make
```
