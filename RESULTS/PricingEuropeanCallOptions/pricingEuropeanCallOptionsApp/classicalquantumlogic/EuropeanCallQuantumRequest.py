# -*- coding: utf-8 -*-
import qiskit, qiskit.circuit, qiskit_finance.circuit


class EuropeanCallQuantumRequest():
	
	def __init__(self):
		self.QUANTUM_COMPUTER = qiskit.Aer()
		self.epsilon = float(0.01)
		self.alpha = float(0.05)
		self.shots = int(1000)
	
	def setParameters(self, epsilon: float, alpha: float, shots: int):
		pass
	
	def getQuantumInstance(self):
		pass
	
	def getQUANTUM_COMPUTER(self):
		return self.QUANTUM_COMPUTER
	
	def setQUANTUM_COMPUTER(self, value):
		self.QUANTUM_COMPUTER = value
	
	def getepsilon(self):
		return self.epsilon
	
	def setepsilon(self, value):
		self.epsilon = value
	
	def getalpha(self):
		return self.alpha
	
	def setalpha(self, value):
		self.alpha = value
	
	def getshots(self):
		return self.shots
	
	def setshots(self, value):
		self.shots = value
	

