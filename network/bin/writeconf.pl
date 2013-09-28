#!/usr/bin/env perl

use Expect; # lang/p5-Expect
use Net::Ping;
use strict;

my $switch = shift(@ARGV);
my $host = "$switch.switch.aub.dk";
my $helper = "/usr/bin/scp -o StrictHostKeyChecking=no configs/$switch $host:/cfg/startup-config";
my $pw = "ma10sse";
my $exp = new Expect;

my $p = Net::Ping->new();

die "$switch is not responding" unless $p->ping($host);
my $c = 0;

my $pid = fork;
if (not defined $pid) {
  die "cannot allocate ressources";
} elsif ($pid == 0) {
#child
  $exp->raw_pty(1);
  $exp->log_stdout(0);

  $exp->spawn($helper) or die "Cannot spawn $helper: $!\n";

  $exp->expect(5, 'password:');
  $exp->send("$pw\n");

  $exp->expect(undef, 'closed by remote host');
} else {
#parent
  while($p->ping($host) && $c < 10) {
    sleep 1;
    $c++;
  }

  kill("TERM", $pid); 

}
 
exit($c == 10);
