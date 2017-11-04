#include<stdio.h>
#include <errno.h>

int main(int argc, char* argv[]) {
	FILE *file = fopen("testdata.bin", "w");
	if (errno != 0) {
		printf("Could not open file.");
		return 1;
	}

	signed char sb = -128;
	fwrite(&sb, sizeof(char), 1, file);
	sb = -42;
	fwrite(&sb, sizeof(char), 1, file);
	sb = 0;
	fwrite(&sb, sizeof(char), 1, file);
	sb = 42;
	fwrite(&sb, sizeof(char), 1, file);
	sb = 127;
	fwrite(&sb, sizeof(char), 1, file);

	unsigned char ub = 0;
	fwrite(&ub, sizeof(char), 1, file);
	ub = 42;
	fwrite(&ub, sizeof(char), 1, file);
	ub = 255;
	fwrite(&ub, sizeof(char), 1, file);

	signed short ss = -32768;
	fwrite(&ss, sizeof(short), 1, file);
	ss = -42;
	fwrite(&ss, sizeof(short), 1, file);
	ss = 0;
	fwrite(&ss, sizeof(short), 1, file);
	ss = 42;
	fwrite(&ss, sizeof(short), 1, file);
	ss = 32767;
	fwrite(&ss, sizeof(short), 1, file);

	unsigned short us = 0;
	fwrite(&us, sizeof(short), 1, file);
	us = 42;
	fwrite(&us, sizeof(short), 1, file);
	us = 65535;
	fwrite(&us, sizeof(short), 1, file);

	signed int si = -2147483648;
	fwrite(&si, sizeof(int), 1, file);
	si = -42;
	fwrite(&si, sizeof(int), 1, file);
	si = 0;
	fwrite(&si, sizeof(int), 1, file);
	si = 42;
	fwrite(&si, sizeof(int), 1, file);
	si = 2147483647;
	fwrite(&si, sizeof(int), 1, file);

	unsigned int ui = 0;
	fwrite(&ui, sizeof(int), 1, file);
	ui = 42;
	fwrite(&ui, sizeof(int), 1, file);
	ui = 4294967295;
	fwrite(&ui, sizeof(int), 1, file);

	long long sl = -9223372036854775807;
	fwrite(&sl, sizeof(long long), 1, file);
	sl = -42;
	fwrite(&sl, sizeof(long long), 1, file);
	sl = 0;
	fwrite(&sl, sizeof(long long), 1, file);
	sl = 42;
	fwrite(&sl, sizeof(long long), 1, file);
	sl= 9223372036854775807;
	fwrite(&sl, sizeof(long long), 1, file);

	float f = -1.2345678;
	fwrite(&f, sizeof(float), 1, file);
	f = -42.0;
	fwrite(&f, sizeof(float), 1, file);
	f = 0.0;
	fwrite(&f, sizeof(float), 1, file);
	f = 42.0;
	fwrite(&f, sizeof(float), 1, file);
	f = 1.2345678;
	fwrite(&f, sizeof(float), 1, file);

	double d = -1.234567890123;
	fwrite(&d, sizeof(double), 1, file);
	d = -42.0;
	fwrite(&d, sizeof(double), 1, file);
	d = 0.0;
	fwrite(&d, sizeof(double), 1, file);
	d = 42.0;
	fwrite(&d, sizeof(double), 1, file);
	d = 1.234567890123;
	fwrite(&d, sizeof(double), 1, file);

	fputs("Hello, world!", file);
	fputc(0, file);

	fclose(file);
}
