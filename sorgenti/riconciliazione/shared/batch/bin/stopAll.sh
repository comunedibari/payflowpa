#!/bin/sh
ps -ef | grep Batch | grep -v grep | awk '{print $2}' | xargs kill -9
