# -*- coding: utf-8 -*-

from qiskit import QuantumCircuit, QuantumRegister, ClassicalRegister
from numpy import pi

def w_state() -> QuantumCircuit:
	q0 = QuantumRegister(1, 'q0')
	q1 = QuantumRegister(1, 'q1')
	q2 = QuantumRegister(1, 'q2')
	
	register1 = ClassicalRegister(1, 'register1')
	register2 = ClassicalRegister(1, 'register2')
	register3 = ClassicalRegister(1, 'register3')
	
	w_state = QuantumCircuit(q0, q1, q2, register1, register2, register3)
	
	w_state.ry(1.9106332362490184, q0)
	w_state.cu(pi/2, pi/2, pi/2, pi/2, q0, q1)
	w_state.cx(q1, q2)
	w_state.cx(q0, q1)
	w_state.x(q0)
	w_state.measure(q0, register1)
	w_state.measure(q1, register2)
	w_state.measure(q2, register3)
	
	return w_state
