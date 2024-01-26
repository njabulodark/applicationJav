class ProblemAlG007a:
    def __init__(self, string):
        self.str = string
        self.lis = string.split(" ")
        self.checked = []
        self.temp_lis = []
        self.clean_lis = []
        self.combo_pair = []
        
    def prepair(self):
        for i in self.lis:
            if i not in self.temp_lis: 
                self.clean_lis.append(i)
                self.temp_lis.append(i)
                
    def results(self):
        lenght = len(self.lis)
        self.temp_lis = []
        self.prepair()

        for control in self.clean_lis:
            # control
            pair = 0
            next_pair= []
            for i in range(lenght):
                if self.lis[i] == control:
                    for element in range(i+1, lenght):
                        if control == self.lis[element]: self.combo_pair.append((i, element))

                    # check for next to pair
                    try:
                        if self.lis[i] == self.lis[i+1]:
                            pair+=1
                            next_pair.append((i, i+1))
                    except:
                        pass

            # make variables
            combo_pair_str = ""
            # combo_pair_str
            try:
                combo_pair_str = " ".join([str(item) for item in self.combo_pair])
            except:
                pass

            try:
                next_pair = " ".join([str(item) for item in next_pair])
            except:
                next_pair = ""

            print(f'{control}: {len(self.combo_pair)} combo-pairs {combo_pair_str} AND {pair} next-to-pairs {next_pair}')

            self.combo_pair = []
            pair = 0

if __name__ == "__main__":
    # test
    problem = ProblemAlG007a("spring java spring spring spring javascript java jboss jboss tomcat jboss")
    problem.results()

    # test with input
    # problem = ProblemAlG007a(input("please enter the text you would like to test: "))
    # problem.results()
