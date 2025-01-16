# Start Script Setting
inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"

# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# about how to setup ssh-agent for passwordless access
SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-kimthon.git;protocol=ssh;branch=main"

PV = "1.0+git${SRCPV}"
SRCREV = "95ba918a03b2fcd7b8edce81ea2d55a1ffa6f9a5"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/aesdsocket ${S}/aesdsocket-start-stop"
TARGET_LDFLAGS += "-pthread -lrt"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
        install -d ${D}${bindir}
        install -m 0755 ${S}/aesdsocket ${D}${bindir}/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d
}
