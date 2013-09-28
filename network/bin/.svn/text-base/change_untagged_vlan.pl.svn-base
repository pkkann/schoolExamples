#!/usr/bin/env perl

use Expect; # lang/p5-Expect
use strict;

$Expect::Log_Stdout = 0;

my $cmd = "telnet " . $ARGV[0];
my $port = $ARGV[1];
my $vlan = $ARGV[2];
my $disable = $ARGV[3];
my $exp = new Expect;

$exp->raw_pty(1);
$exp->spawn($cmd) or die "Cannot spawn $cmd: $!\n";

$exp->expect(5, 'Press any key to continue');
$exp->send("\n");
$exp->expect(5, 'Password:');
$exp->send("ma10sse\n");

$exp->expect(5, '#');
$exp->send("conf\n");

$exp->expect(5, '(config)#');
$exp->send("vlan $vlan\n");

$exp->expect(5, "(vlan-$vlan)#");
if ($disable) {
  $exp->send("no ");
}
$exp->send("untagged $port\n");

$exp->expect(5, "(vlan-$vlan)#");
