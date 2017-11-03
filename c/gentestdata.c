#include<stdio.h>
#include <errno.h>

int main(int argc, char* argv[]) {
	FILE *file = fopen("testdata.bin", "w");
	if (errno != 0) {
		printf("Could not open file.");
		return 1;
	}

	fputs("hello world!", file);
	fputc(0, file);
	fputs("hello world!", file);
	fputc(0, file);
	double d = 3;
	fwrite(&d, sizeof(double), 1, file);
	d = 1.0;
	fwrite(&d, sizeof(double), 1, file);

	fclose(file);
}
