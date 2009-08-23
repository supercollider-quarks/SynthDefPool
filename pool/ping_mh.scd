{\rtf1\ansi\ansicpg1252\cocoartf949\cocoasubrtf460
{\fonttbl\f0\fnil\fcharset0 Monaco;}
{\colortbl;\red255\green255\blue255;\red0\green0\blue191;\red0\green0\blue0;\red0\green115\blue0;
\red96\green96\blue96;}
\deftab560
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardeftab560\ql\qnatural\pardirnatural

\f0\fs24 \cf2 SynthDef\cf3 (\cf4 \\ping_mh\cf3 ,\{\cf2 arg\cf3  freq=440,amp=0.2,dur=1,attack=0.001,pan=0,out=0;\
    \cf2 var\cf3  sig,freq2;\
    freq=freq*rrand(1,1.01);\
    freq2=freq*rrand(1,1.01);\
    e=\cf2 EnvGen\cf3 .ar(\cf2 Env\cf3 .perc(attack,dur,curve:-4),doneAction:2);\
    sig=\cf2 SinOsc\cf3 .ar([freq,freq2],0,amp*e);\
    sig=\cf2 Pan2\cf3 .ar(sig,pan);\
    \cf2 Out\cf3 .ar(out,sig)\
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\ql\qnatural\pardirnatural
\cf3 \},\
metadata: (\
	credit: \cf5 "Your basic percussive synth instrument, a good default sound for testing patterns, etc."\cf3 ,\
	tags: [\cf4 \\percussive\cf3 , \cf4 \\ping\cf3 , \cf4 \\default\cf3 , \cf4 \\simple\cf3 , \cf4 \\stereo\cf3 , \cf4 \\detuned\cf3 , \cf4 \\tuned\cf3 ]\
	)\
)}